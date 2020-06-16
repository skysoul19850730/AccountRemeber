package com.jscoolstar.accountremeber.apps

import com.jscoolstar.accountremeber.model.SSResult
import com.jscoolstar.accountremeber.model.beans.User
import com.jscoolstar.accountremeber.model.repository.local.UserRepositoryLocal
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager
import org.koin.core.KoinComponent
import org.koin.core.inject


class UserInfoManager : KoinComponent {

    private var user: User? = null


    val userDao: UserRepositoryLocal by inject()

    fun getUser():User{
        return  User()
    }
    fun logout(){

    }

    suspend fun getUserAndReturnLoginIfNull(): User {
        if (user == null) {
            user = getLastUser()
        }
        if (user == null) {
            return returnEmptyUserAddGoBackLogin()
        }
        return user!!
    }

    suspend fun getLastUser(): User? {
        val curUserId = SharedPreferencesManager.getInt(SharedPreferencesManager.userid, 0)
        if (curUserId == 0) return null
        return userDao.getUserById(curUserId)?.run {
            when (this) {
                is SSResult.Success -> data
                else -> null
            }
        }
    }

    private fun returnEmptyUserAddGoBackLogin(): User {
        ActivityManager.goLogin(MApplication.getInstance().getContext())
        return User()
    }

    companion object {
        @Volatile
        private var instance: UserInfoManager? = null

        fun getInstance(): UserInfoManager {
            if (instance == null) {
                synchronized(UserInfoManager.javaClass) {
                    if (instance == null)
                        instance = UserInfoManager()
                }

            }
            return instance!!
        }
    }

    suspend fun save4LastUser(userName: String, userId: Int) {
        SharedPreferencesManager.setString(SharedPreferencesManager.lastUserName, userName)
        SharedPreferencesManager.setInt(SharedPreferencesManager.userid, userId)
        user = getLastUser()
    }

    fun clearUser() {
        SharedPreferencesManager.setInt(SharedPreferencesManager.userid, 0)
        user = null;
    }
}
