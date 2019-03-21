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

    override fun addUser(user: User, password: String): Boolean {
        var dm = user.toDMUser(password)
        return usermodel.addUser(dm)
    }

    override fun updateUserBaseInfo(user: User): Boolean {
        return usermodel.updateUserBaseInfo(user.toDMUser(null))
    }

    override fun updateUserPassword(userId: Int, password: String, passwordTip: String?): Boolean {
        return usermodel.updateUserPassword(userId,password,passwordTip)
    }

    override fun checkPassword(userName: String, password: String): Int {
        return usermodel.checkPassword(userName,password)
    }

    override fun isUserNameExsits(userName: String): Boolean {
        return usermodel.isUserNameExsits(userName)
    }

    override fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String> {

        return usermodel.getUserReTryTimesAndLastWrongTime(userName)
    }
}