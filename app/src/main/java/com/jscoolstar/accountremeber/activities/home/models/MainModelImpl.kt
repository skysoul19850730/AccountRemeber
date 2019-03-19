package com.jscoolstar.accountremeber.activities.home.models

import com.jscoolstar.accountremeber.dataprovider.AccountModel
import com.jscoolstar.accountremeber.dataprovider.AccountModelImpl
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account

class MainModelImpl :MainModel {

    var accountModel: AccountModel
    constructor(){
        accountModel = AccountModelImpl()
    }

    override fun getAllAccounts(userId:Int): List<Account> {
        return accountModel.getAccountListAll(userId)
    }

    override fun deleteAccounts(accounts: List<Account>): Boolean {
        var allDeleted = true
        for (account in accounts){
            if(!accountModel.deleteAccount(account)){
                allDeleted = false
            }
        }
        return allDeleted
    }
}