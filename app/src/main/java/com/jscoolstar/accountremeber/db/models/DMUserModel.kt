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
    /**
     * @date   2019/10/29 18:35
     * @Description 注册用户，成功后返回用户id，否则返回-1
     */
    fun addUser(userName: String,password: String,passwordTip: String?):Long
    /** 更新用户信息，不含密码  */
    fun updateUserBaseInfo(dmUser: DMUser):Boolean
    /** 更新登录密码和密码提示  */
    fun updateUserPassword4Login(userId:Int, password:String, passwordTip:String?):Boolean
    /** 0位置如果大于0，代表用户正确，返回userid。否则代表不正确。 1位置代表，还可以尝试的次数  */
    fun checkPassword(userName: String,password: String):Pair<Int,Int>

    fun isUserNameExsits(userName:String):Boolean
    fun isNickNameExsits(nickName:String):Boolean

    fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>

    fun isAccountViewPasswordCorrect(userId: Int,password: String):Boolean
    fun isAccountViewPasswordUseLoginPassword(userId: Int):Boolean
    /** 更新查看账户详情密码  */
    fun updateUserPassword4ViewAccount(userId:Int, password:String?,isUseLoginPassword:Boolean):Boolean
}