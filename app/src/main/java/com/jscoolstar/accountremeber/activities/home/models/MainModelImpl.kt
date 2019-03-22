package com.jscoolstar.accountremeber.activities.home.models

import com.jscoolstar.accountremeber.dataprovider.AccountModel
import com.jscoolstar.accountremeber.dataprovider.AccountModelImpl
import com.jscoolstar.accountremeber.dataprovider.UserModel
import com.jscoolstar.accountremeber.dataprovider.UserModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.User
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.models.DMUserModelImpl
import com.jscoolstar.accountremeber.utils.SharedPreferencesManager

class MainModelImpl : MainModel {

    var accountModel = AccountModelImpl()
    var userModel = UserModelImpl()

    override fun getAllAccounts(userId: Int): List<Account> {
        return accountModel.getAccountListAll(userId,0)
    }

    override fun deleteAccounts(accounts: List<Account>): Boolean {
        var allDeleted = true
        for (account in accounts) {
            if (!accountModel.deleteAccount(account)) {
                allDeleted = false
            }
        }
        return allDeleted
    }

    override fun getCurrectUser(): User? {
        var userId = SharedPreferencesManager.getInt(SharedPreferencesManager.userid)
        if(userId==0)return null
        return userModel.getUserById(userId)
    }

    override fun getUserReTryTimesAndLastWrongTime(userName: String):Pair<Int,String>{
        return userModel.getUserReTryTimesAndLastWrongTime(userName)
    }
}