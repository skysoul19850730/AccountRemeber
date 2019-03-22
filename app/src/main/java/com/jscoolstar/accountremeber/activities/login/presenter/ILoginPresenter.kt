package com.jscoolstar.accountremeber.activities.login.presenter

import com.jscoolstar.accountremeber.activities.BasePresenter

interface ILoginPresenter:BasePresenter {

    fun onLoginClick(userName:String ,password:String)

    fun onRegisterClick()
}