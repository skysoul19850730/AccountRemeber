package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.model.beans.User

interface UserModel {

    fun getUserById(userId: Int): User?

    fun getUserList():ArrayList<User>

    fun addUser(userName: String,password: String,passwordTip: String?):Long

    fun updateUserBaseInfo(user: User):Boolean

    fun updateUserPassword4Login(userId:Int,password:String,passwordTip:String?):Boolean

    /** -1代表密码正确,其他值代表，还可以尝试的次数  */
    fun checkPassword(userName: String,password: String):Pair<Int,Int>

    fun isUserNameExsits(userName:String):Boolean
    fun isNickNameExsits(nickName:String):Boolean

    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>

    fun isAccountViewPasswordCorrect(userId: Int,password: String):Boolean
    fun isAccountViewPasswordUseLoginPassword(userId: Int):Boolean
    /** 更新查看账户详情密码  */
    fun updateUserPassword4ViewAccount(userId:Int, password:String?,isUseLoginPassword:Boolean):Boolean
}