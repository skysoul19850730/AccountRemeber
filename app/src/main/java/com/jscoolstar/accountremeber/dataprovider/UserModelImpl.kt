package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.db.models.DMUserModelImpl

class UserModelImpl :UserModel {

    var usermodel = DMUserModelImpl()

    override fun getUserById(userId: Int): User? {
        return usermodel.getUserById(userId)?.toUser()
    }

    override fun getUserList(): ArrayList<User> {
        var list = usermodel.getUserList()
        var results = arrayListOf<User>()

        for(dm in list){
            results.add(dm.toUser())
        }

        return results
    }

    override fun addUser(userName: String,password: String,passwordTip: String?): Long {
        var result = usermodel.addUser(userName,password,passwordTip)
        return result
    }

    override fun updateUserBaseInfo(user: User): Boolean {
        return usermodel.updateUserBaseInfo(user.toDMUser())
    }

    override fun updateUserPassword4Login(userId: Int, password: String, passwordTip: String?): Boolean {
        return usermodel.updateUserPassword4Login(userId,password,passwordTip)
    }

    override fun checkPassword(userName: String, password: String): Pair<Int,Int>  {
        return usermodel.checkPassword(userName,password)
    }

    override fun isUserNameExsits(userName: String): Boolean {
        return usermodel.isUserNameExsits(userName)
    }

    override fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String> {

        return usermodel.getUserReTryTimesAndLastWrongTime(userName)
    }

    override fun isNickNameExsits(nickName: String): Boolean {
        return usermodel.isNickNameExsits(nickName)
    }

    override fun isAccountViewPasswordCorrect(userId: Int, password: String): Boolean {
        return usermodel.isAccountViewPasswordCorrect(userId,password)
    }

    override fun isAccountViewPasswordUseLoginPassword(userId: Int): Boolean {
        return usermodel.isAccountViewPasswordUseLoginPassword(userId)
    }

    override fun updateUserPassword4ViewAccount(userId: Int, password: String?, isUseLoginPassword: Boolean): Boolean {
        return usermodel.updateUserPassword4ViewAccount(userId,password,isUseLoginPassword)
    }
}