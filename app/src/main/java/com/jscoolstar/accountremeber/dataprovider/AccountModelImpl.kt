package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.db.models.DMAccountModelImpl

class AccountModelImpl : AccountModel {
    var dmAccountModelImpl: DMAccountModelImpl

    constructor() {
        dmAccountModelImpl = DMAccountModelImpl()
    }

    override fun getAccountListAll(userId: Int): List<Account> {
        var dmList = dmAccountModelImpl.getAccountListAll(userId)
        var results = dmAccountList2AccountList(dmList)
        return results
    }

    override fun addAccount(userId: Int, account: Account): Boolean {
        account.userId = userId
        return dmAccountModelImpl.addAccount(account.toDMAccount())
    }

    override fun deleteAccount(account: Account): Boolean {
        return dmAccountModelImpl.deleteAccount(account.toDMAccount())
    }

    override fun getAccountListWithKeyWord(userId: Int, key: String): List<Account> {
        var dmList = dmAccountModelImpl.getAccountListWithKeyWord(key, userId)
        var results = dmAccountList2AccountList(dmList)
        return results
    }

    private fun dmAccountList2AccountList(dmList: List<DMAccount>): List<Account> {
        var results = arrayListOf<Account>()
        for (dm in dmList) {
            var cate = CateModelImpl().getCateById(dm.cateId)
            var account = dm.toAccount()
            account.cate = CateModelImpl().getCateById(dm.cateId)
            account.extraColumnList = ExtraColumnModelImpl().getColumnsWithAccountID(account.id)
            results.add(account)
        }
        return results
    }
}