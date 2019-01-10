package com.jscoolstar.accountremeber.db.models

import com.jscoolstar.accountremeber.db.entity.DMAccount

interface DMAccountModel {
    fun getAccountListAll(userId:Int): List<DMAccount>
    fun addAccount(account: DMAccount): Boolean
    fun deleteAccount(account: DMAccount): Boolean
    fun getAccountListWithKeyWord(key: String,userId: Int): List<DMAccount>
}