package com.jscoolstar.accountremeber.models.account

import com.jscoolstar.accountremeber.db.entity.DMAccount

/**
 * Created by Administrator on 2018/4/2.
 */
interface AccountModel {
    fun getAccountListAll(): List<DMAccount>
    fun addAccount(account: DMAccount): Boolean
    fun deleteAccount(account: DMAccount): Boolean
    fun getAccountListWithKeyWord(key: String): List<DMAccount>

}