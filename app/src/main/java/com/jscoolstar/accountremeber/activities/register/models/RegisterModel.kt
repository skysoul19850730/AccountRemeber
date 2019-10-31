package com.jscoolstar.accountremeber.activities.register.models

import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface RegisterModel {

    fun isUserNameExsits(userName:String):Boolean

    fun addUser(userName: String,password: String,passwordTip: String?): Long

}