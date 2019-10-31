package com.jscoolstar.accountremeber.activities.register.presenters

import android.text.TextUtils
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.activities.register.models.Register2ModelImpl
import com.jscoolstar.accountremeber.activities.register.models.RegisterModelImpl
import com.jscoolstar.accountremeber.activities.register.views.Register2View
import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.jscoolstarlibrary.widgets.dialog.JSMaterialDialogUtil

class RegisterPresenter2Impl(var viewModel: Register2View?, val dataModel: Register2ModelImpl):IRegisterPresenter2 {

    init {
        viewModel?.presenter = this
    }

    override fun onOkClick(nickName: String, password: String?, isUseLoginPasswor: Boolean) {
        if (nickName.isEmpty()) {
            viewModel?.showToast(getString(R.string.register2_nickname_cannot_be_null))
            return
        }
        if(dataModel.isNickNameExsits(nickName)){
            viewModel?.showToast(getString(R.string.register2_nickname_already_exist))
            return
        }
        if(!isUseLoginPasswor && TextUtils.isEmpty(password)){
            viewModel?.uiShowNoAccountPasswordTipDialot{ onDialogConfirmClickOk(nickName) }
            return
        }
        dataModel.setNickName(nickName)
        dataModel.updateUserPassword4ViewAccount(UserInfoManager.getInstance().getUser()!!.userId,password,isUseLoginPasswor)
        viewModel?.uiShowHome()
    }

    private fun onDialogConfirmClickOk(nickName: String) {
        dataModel.setNickName(nickName)
        dataModel.updateUserPassword4ViewAccount(UserInfoManager.getInstance().getUser()!!.userId,"",false)
        viewModel?.uiShowHome()
    }


    override fun destory() {
        viewModel=null
    }

    override fun start() {
        viewModel?.uiSwithOpen(dataModel.isUserUseLoginPassword())
    }
}