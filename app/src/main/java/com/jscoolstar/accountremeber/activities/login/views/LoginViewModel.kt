package com.jscoolstar.accountremeber.activities.login.views

import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.login.presenter.ILoginPresenter

interface LoginViewModel :BaseView<ILoginPresenter>{


    fun uiShowRegister()//去注册页
    fun uiShowHome()//去首页
    fun showPasswordInput(show:Boolean)//“*”或显形  显示密码


}