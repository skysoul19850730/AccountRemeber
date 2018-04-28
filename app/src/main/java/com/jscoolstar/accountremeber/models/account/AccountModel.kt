package com.jscoolstar.accountremeber.models.account

import com.jscoolstar.accountremeber.db.entity.Account

/**
 * Created by Administrator on 2018/4/2.
 */
interface AccountModel {
    fun getAccountListAll(): List<Account>
    fun addAccount(account: Account): Boolean
    fun deleteAccount(account: Account): Boolean
    fun getAccountListWithKeyWord(key: String): List<Account>

}