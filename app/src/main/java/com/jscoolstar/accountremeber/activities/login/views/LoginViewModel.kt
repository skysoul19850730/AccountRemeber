package com.jscoolstar.accountremeber.activities.login.views

import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.login.presenter.ILoginPresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface LoginViewModel :BaseView<ILoginPresenter>{


    fun uiShowRegister()//去注册页
    fun uiShowHome()//去首页
    fun showUIWithUser(userName:String?)

    fun showPasswordWrong(msg:String)
    fun showUserNameWrong(msg:String)
}