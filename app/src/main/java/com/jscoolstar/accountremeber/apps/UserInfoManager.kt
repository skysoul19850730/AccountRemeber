package com.jscoolstar.accountremeber.apps

import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager

class UserInfoManager {

    private var user: User? = null

    fun getUser(): User {
        if (user == null) {
            val curUserId = SharedPreferencesManager.getInt(SharedPreferencesManager.userid, 0)
            if (curUserId == 0) return returnEmptyUserAddGoBackLogin()
            user = UserModelImpl().getUserById(curUserId)
        }
        if(user==null){
            return returnEmptyUserAddGoBackLogin()
        }
        return user!!
    }

    fun getLastUser():User?{
        val curUserId = SharedPreferencesManager.getInt(SharedPreferencesManager.userid, 0)
        if(curUserId == 0)return null
        var user = UserModelImpl().getUserById(curUserId)
        return user
    }

    private fun returnEmptyUserAddGoBackLogin():User{
        ActivityManager.goLogin(MApplication.getInstance().getContext())
        return User()
    }

    companion object {
        private var instance: UserInfoManager? = null

        fun getInstance(): UserInfoManager {
            if (instance == null) {
                instance = UserInfoManager()
            }
            return instance!!
        }
    }

    fun save4LastUser(userName:String,userId:Int){
        SharedPreferencesManager.setString(SharedPreferencesManager.lastUserName,userName)
        SharedPreferencesManager.setInt(SharedPreferencesManager.userid,userId)
        user = UserModelImpl().getUserById(userId)
    }

    fun logout(){
        SharedPreferencesManager.setInt(SharedPreferencesManager.userid,0)
        user = null;
    }
}
