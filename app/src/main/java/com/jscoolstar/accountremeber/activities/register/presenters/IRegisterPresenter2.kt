package com.jscoolstar.accountremeber.activities.register.presenters

import com.jscoolstar.accountremeber.activities.BasePresenter

interface IRegisterPresenter2 : BasePresenter {

    fun onOkClick(nickName: String, password: String?,isUseLoginPasswor:Boolean)
//    fun onDialogConfirmClickOk(nickName: String)

}