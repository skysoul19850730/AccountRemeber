package com.jscoolstar.accountremeber.model.repository

import com.jscoolstar.accountremeber.dbroom.entities.NoLoginUser
import com.jscoolstar.accountremeber.model.SSResult
import com.jscoolstar.accountremeber.model.beans.User
import com.jscoolstar.accountremeber.model.repository.resultbeans.LoginResult
import java.util.function.BiPredicate

interface UserRepository {

    suspend fun getUserById(userId: Int): SSResult<User>
    suspend fun getNoLoginUserList(): SSResult<List<NoLoginUser>>
    suspend fun registerWith(userName: String, password: String, passwordTip: String): SSResult<User>
    suspend fun updateUserBaseInfo(user: User): SSResult<Int>
    suspend fun updateUserPassword4Login(userId:Int,password:String,passwordTip:String):SSResult<Boolean>
    /**
     * 检测密码，并处理错误尝试次数减一的逻辑等，[type] 代表要验证的密码类型，0是登陆密码，1是view密码
     * 返回值[SSResult<Pair<Int,User?>>] 异常会返回[SSResult.Error]
     * [SSResult.Success] 返回一个pair user 不为空代表验证成功，int -1代表用户不存在 ，其他代表剩余尝试次数
     */
    suspend fun checkPasswordWithType(type:Int,userName: String, password: String): SSResult<LoginResult>
    suspend fun isNickNameExist(nickName:String):SSResult<Boolean>
    /** 更新查看账户详情密码  */
    suspend fun updateUserPassword4ViewAccount(userId:Int, password:String,passwordTip: String):SSResult<Boolean>


}