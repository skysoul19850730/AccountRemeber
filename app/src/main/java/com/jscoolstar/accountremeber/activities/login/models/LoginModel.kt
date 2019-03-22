package com.jscoolstar.accountremeber.activities.login.models

interface LoginModel {


    fun getLastUserName():String?
    /** -1代表密码正确,其他值代表，还可以尝试的次数  */
    fun checkPassword(userName: String,password: String):Int
    fun isUserNameExsits(userName:String):Boolean
    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>
}