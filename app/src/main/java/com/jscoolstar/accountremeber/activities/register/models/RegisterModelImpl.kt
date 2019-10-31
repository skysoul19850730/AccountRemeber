package com.jscoolstar.accountremeber.activities.register.models

import com.jscoolstar.accountremeber.activities.login.models.LoginModelImpl
import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

class RegisterModelImpl : RegisterModel {

    var userModel = UserModelImpl()


    override fun isUserNameExsits(userName: String): Boolean {
        return userModel.isUserNameExsits(userName)
    }

    override fun addUser(userName: String,password: String,passwordTip: String?): Long {
        return userModel.addUser(userName,password,passwordTip)
    }
}