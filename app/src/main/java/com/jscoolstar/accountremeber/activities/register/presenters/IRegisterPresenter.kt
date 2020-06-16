package com.jscoolstar.accountremeber.activities.register.presenters

import com.jscoolstar.accountremeber.activities.BasePresenter

interface IRegisterPresenter : BasePresenter {

    fun onRegisterClick(userName: String, password: String, password2: String,passwordTip:String)

}