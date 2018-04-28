package com.jscoolstar.accountremeber.activities

import com.jscoolstar.accountremeber.db.entity.Account
import com.jscoolstar.accountremeber.models.account.AccountModel
import com.jscoolstar.accountremeber.models.account.AccountModelImpl

/**
 * Created by Administrator on 2018/4/16.
 */
class MainActPressent {

    lateinit var accountModelImpl: AccountModel
    lateinit var mainActModel: MainActViewModel

    constructor(mainActViewModel: MainActViewModel) {
        mainActModel = mainActViewModel
        accountModelImpl = AccountModelImpl()
    }

    fun getList(): List<Account> {
        var list = accountModelImpl.getAccountListAll()
        return list
    }

    fun addOneAccount(account: Account) {
        accountModelImpl.addAccount(account)
    }

    fun deleteAccounts(accounts: ArrayList<Account>) {
        for (account in accounts)
            accountModelImpl.deleteAccount(account)
    }

}