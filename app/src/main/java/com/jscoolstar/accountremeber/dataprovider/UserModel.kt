package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface UserModel {

    fun getUserList():List<User>

    fun addUser(user:User,password:String):Boolean

    fun updateUserBaseInfo(user: User):Boolean

    fun updateUserPassword(userId:Int,password:String,passwordTip:String?):Boolean

    /** -1代表密码正确,其他值代表，还可以尝试的次数  */
    fun checkPassword(userName: String,password: String):Int

    fun isUserNameExsits(userName:String):Boolean

    fun getUserReTryTimes(userName: String):Int
}