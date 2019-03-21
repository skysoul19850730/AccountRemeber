package com.jscoolstar.accountremeber.db.models

import com.jscoolstar.accountremeber.db.entity.DMUser
/**
 * @date   2019/1/10 16:18
 * @author sqc
 * @Description 用户管理，获取用户信息时不能获取密码，添加用户时可以带密码，更新用户信息时没有密码，只能单独调用更新密码方法
 */
interface DMUserModel {

    fun getUserById(userId:Int):DMUser?
    fun getUserList():ArrayList<DMUser>
    fun addUser(dmUser: DMUser):Boolean
    /** 更新用户信息，不含密码  */
    fun updateUserBaseInfo(dmUser: DMUser):Boolean
    /** 更新密码和密码提示  */
    fun updateUserPassword(userId:Int,password:String,passwordTip:String?):Boolean
    /** -1代表密码正确,其他值代表，还可以尝试的次数  */
    fun checkPassword(userName: String,password: String):Int

    fun isUserNameExsits(userName:String):Boolean

    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>
}