package com.jscoolstar.accountremeber.activities.login.models

import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager

class LoginModelImpl:LoginModel {

    var userModel = UserModelImpl()

    override fun getLastUserName(): String? {
        return SharedPreferencesManager.getString(SharedPreferencesManager.lastUserName)
    }

    override fun checkPassword(userName: String, password: String): Int {
        return userModel.checkPassword(userName,password)
    }

    override fun isUserNameExsits(userName: String): Boolean {
        return userModel.isUserNameExsits(userName)
    }

    override fun getUserReTryTimesAndLastWrongTime(userName: String): Pair<Int, String> {
        return userModel.getUserReTryTimesAndLastWrongTime(userName)
    }
}