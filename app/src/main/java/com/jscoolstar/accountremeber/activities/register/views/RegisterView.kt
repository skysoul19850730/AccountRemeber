package com.jscoolstar.accountremeber.activities.register.views

import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.register.presenters.IRegisterPresenter
import com.jscoolstar.accountremeber.activities.register.presenters.RegisterPresenterImpl

interface RegisterView : BaseView<IRegisterPresenter>{

    fun uiShowHome()//去首页
    fun showPasswordWrong(msg:String)
    fun showPasswordWrong2(msg:String)
    fun showUserNameWrong(msg:String)
}