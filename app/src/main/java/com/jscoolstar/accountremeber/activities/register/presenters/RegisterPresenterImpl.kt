package com.jscoolstar.accountremeber.activities.register.presenters

import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.register.models.RegisterModelImpl
import com.jscoolstar.accountremeber.activities.register.views.RegisterView
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager

class RegisterPresenterImpl(var viewModel:RegisterView?,val dataModel:RegisterModelImpl):IRegisterPresenter {

    init {
        viewModel?.presenter = this
    }
    override fun onRegisterClick(userName: String, password: String, password2: String,passwordTip:String) {
        if (userName.isEmpty()) {
            viewModel?.showUserNameWrong(getString(R.string.login_username_cannot_be_null))
            return
        }
        if (dataModel.isUserNameExsits(userName)) {
            viewModel?.showUserNameWrong(getString(R.string.register_user_exists))
            return
        }


        if (password.isEmpty()) {
            viewModel?.showPasswordWrong(getString(R.string.login_password_cannot_null))
            return
        }
        if (password2.isEmpty()) {
            viewModel?.showPasswordWrong2(getString(R.string.login_password_cannot_null))
            return
        }

        if(!password.equals(password2)){
            viewModel?.showPasswordWrong2(getString(R.string.register_password_different))
            return
        }

        var user = User()
        user.passwordTip = passwordTip
        user.userName = userName
        var result = dataModel.addUser(user,password)
        if(result){
            SharedPreferencesManager.setString(SharedPreferencesManager.lastUserName,userName)
            SharedPreferencesManager.setInt(SharedPreferencesManager.userid,user.userId)
            viewModel?.uiShowHome()
        }else{
            viewModel?.showToast("添加用户失败，请更换用户名或者密码再次尝试")
        }

    }

    override fun destory() {
        viewModel=null
    }

    override fun start() {
    }
}