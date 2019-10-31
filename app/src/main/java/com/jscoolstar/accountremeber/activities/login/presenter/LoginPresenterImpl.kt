package com.jscoolstar.accountremeber.activities.login.presenter

import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.login.models.LoginModel
import com.jscoolstar.accountremeber.activities.login.views.LoginViewModel
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager
import com.jscoolstar.accountremeber.utils.Util

class LoginPresenterImpl(var viewModel: LoginViewModel?, var dataModel: LoginModel) : ILoginPresenter {

    init {
        viewModel?.presenter = this
    }

    override fun destory() {
        viewModel = null
    }

    override fun onLoginClick(userName: String, password: String) {
        if (userName.isEmpty()) {
            viewModel?.showUserNameWrong(getString(R.string.login_username_cannot_be_null))
            return
        }
        if (password.isEmpty()) {
            viewModel?.showPasswordWrong(getString(R.string.login_password_cannot_null))
            return
        }

        if (!dataModel.isUserNameExsits(userName)) {
            viewModel?.showUserNameWrong(getString(R.string.login_user_not_exists))
            return
        }
        if (checkWrongTimes(userName)) return

        var result = dataModel.checkPassword(userName, password)
        if(result.first>0){
            loginSuc2Home(userName,result.first)
            return
        }
        when(result.second){
            0 -> checkWrongTimes(userName)
            else ->viewModel?.showToast(String.format(getString(R.string.login_password_wrong),result.second))
        }
    }

    private fun loginSuc2Home(userName: String,userId:Int){
        UserInfoManager.getInstance().save4LastUser(userName,userId)
        viewModel?.uiShowHome()
    }

    private fun checkWrongTimes(userName: String): Boolean {
        var result = dataModel.getUserReTryTimesAndLastWrongTime(userName)
        if (result.first == 0) {
            var time = Util.INSTANCE.formatTime(result.second.toLong())
            var formatedTime = String.format(getString(R.string.password_wrong_moretimes), time)
            viewModel?.showToast(formatedTime)
            return true
        }
        return false
    }

    override fun onRegisterClick() {
        viewModel?.uiShowRegister()
    }

    override fun start() {

        if(UserInfoManager.getInstance().getLastUser()!=null){
            viewModel?.uiShowHome()//如果有上次登录用户未登出，则直接进入首页
            return
        }

        var lastUserName = dataModel.getLastUserName()
        viewModel?.showUIWithUser(lastUserName)
    }
}