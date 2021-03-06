package com.jscoolstar.accountremeber.dataprovider

import com.jscoolstar.accountremeber.model.beans.Account

interface AccountModel {

    fun getAccountListAll(userId:Int,cateId:Int): List<Account>
    fun addAccount(userId:Int,account: Account): Boolean
    fun deleteAccount(account: Account): Boolean
    fun getAccountListWithKeyWord(userId:Int,key: String): List<Account>
}