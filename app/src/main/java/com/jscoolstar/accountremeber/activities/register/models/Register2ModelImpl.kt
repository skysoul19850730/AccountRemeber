package com.jscoolstar.accountremeber.activities.register.models

import com.jscoolstar.accountremeber.apps.UserInfoManager
import com.jscoolstar.accountremeber.dataprovider.UserModelImpl

class Register2ModelImpl : Register2Model {

    private var userModel = UserModelImpl()

    override fun isNickNameExsits(nickName: String): Boolean {
        return userModel.isNickNameExsits(nickName)
    }

    override fun updateUserPassword4ViewAccount(userId: Int, password: String?, isUseLoginPassword: Boolean): Boolean {
        return userModel.updateUserPassword4ViewAccount(userId, password, isUseLoginPassword)
    }

    override fun setNickName(nickName: String) {
        var user = UserInfoManager.getInstance().getUser()
        user!!.nickName = nickName
        userModel.updateUserBaseInfo(user!!)
    }

    override fun isUserUseLoginPassword(): Boolean {
        return userModel.isAccountViewPasswordUseLoginPassword(UserInfoManager.getInstance().getUser()!!.userId)
    }
}