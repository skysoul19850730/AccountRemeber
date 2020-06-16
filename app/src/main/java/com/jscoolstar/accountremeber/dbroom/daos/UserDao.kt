package com.jscoolstar.accountremeber.dbroom.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jscoolstar.accountremeber.dbroom.BaseDao
import com.jscoolstar.accountremeber.dbroom.entities.*

@Dao
interface UserDao : BaseDao<DMUser> {
    @Query("SELECT * from user where id=:userId")
    suspend fun getUserById(userId: Int): DMUser?

    @Query("SELECT * from user where userName=:userName")
    suspend fun getUserByName(userName: String): DMUser?

    @Query("select * from user")
    suspend fun getUserList(): List<DMUser>?

//    /**  获取程序中 使用的用户，需要修改信息时，业务层获取dm对象，并赋值给dmuser进行更新 */
//    @Query("select * from user where id=:userId")
//    fun getSimpleUserById(userId: Int):SimpleUser

    /**  获取未登陆用户列表，供用户选择用户或者切换用户使用 */
    @Query("select userName,nickName from user ")
    suspend fun getUserNameList(): List<NoLoginUser>?

    /**  获取当前尝试密码的用户的剩余次数，及上次错误的时间 */
    @Query("select userName,leftTryTimes,lastWrongTime from user where userName=:userName")
    suspend fun getDmWrongUser(userName: String): DMWrongUser

    /**  验证用户名是否存在 */
    @Query("select userName from user where userName=:userName")
    suspend fun getUserName(userName: String): String?

    /** 验证昵称是否存在  */
    @Query("select nickName from user where nickName = :nickName")
    suspend fun getNickName(nickName: String): String?

}