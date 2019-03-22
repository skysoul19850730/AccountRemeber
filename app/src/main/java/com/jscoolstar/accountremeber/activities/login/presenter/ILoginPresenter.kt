package com.jscoolstar.accountremeber.activities.login.presenter

import com.jscoolstar.accountremeber.activities.BasePresenter
import com.jscoolstar.accountremeber.apps.MApplication

interface ILoginPresenter:BasePresenter {

    fun onLoginClick(userName:String ,password:String)

    fun onRegisterClick()

    fun getString(id:Int):String{
        return MApplication.getInstance().getString(id)
    }
}