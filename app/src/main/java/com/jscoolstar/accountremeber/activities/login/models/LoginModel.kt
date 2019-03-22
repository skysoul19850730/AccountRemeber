package com.jscoolstar.accountremeber.activities.login.models

interface LoginModel {


    fun getLastUserName():String?
    fun checkPassword(userName: String,password: String):Pair<Int,Int>
    fun isUserNameExsits(userName:String):Boolean
    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>
}