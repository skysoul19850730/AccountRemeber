package com.jscoolstar.accountremeber.activities.login.presenter

import com.jscoolstar.accountremeber.activities.login.models.LoginModel
import com.jscoolstar.accountremeber.activities.login.views.LoginViewModel

class LoginPresenterImpl(var viewModel:LoginViewModel,var dataModel:LoginModel):ILoginPresenter {

    init {
        viewModel.presenter = this
    }



    override fun onLoginClick(userName: String, password: String) {
        if(userName.isEmpty() || password.isEmpty()){
            viewModel.showUserNameWrong("")
        }


    }

    override fun onRegisterClick() {
        viewModel.uiShowRegister()
    }

    override fun start() {
        var lastUserName = dataModel.getLastUserName()
        viewModel.showUIWithUser(lastUserName)
    }
}