package com.jscoolstar.accountremeber.activities.register.models

interface RegisterModel {

    fun isUserNameExsits(userName:String):Boolean

    fun addUser(userName: String,password: String,passwordTip: String?): Long

}