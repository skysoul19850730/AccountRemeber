package com.jscoolstar.accountremeber.activities.register.views

import android.provider.ContactsContract
import com.jscoolstar.accountremeber.activities.BaseView
import com.jscoolstar.accountremeber.activities.register.presenters.IRegisterPresenter2
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogClickListerner

interface Register2View : BaseView<IRegisterPresenter2>{

    /**
     * @date   2019/10/30 18:35
     * @Description 方法就去首页，首页模式设置能single top
     */
    fun uiShowHome()//去首页
    fun uiSwithOpen(open:Boolean)
    fun uiShowNoAccountPasswordTipDialot(method:() -> Unit)

//    fun showPasswordWrong(msg:String)
//    fun showNickWrong(msg:String)

}