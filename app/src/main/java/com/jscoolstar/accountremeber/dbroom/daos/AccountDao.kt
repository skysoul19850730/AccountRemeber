package com.jscoolstar.accountremeber.dbroom.daos

import androidx.room.*
import com.jscoolstar.accountremeber.dbroom.BaseDao
import com.jscoolstar.accountremeber.dbroom.entities.AccountWithCateAndExtras
import com.jscoolstar.accountremeber.dbroom.entities.DMAccount
import com.jscoolstar.accountremeber.dbroom.entities.SimpleAccount

@Dao
interface AccountDao:BaseDao<DMAccount> {

    @Query("select id,platform,accountName,tip from account where userId = :userId")
    fun getSimpleAccounts(userId:Int):List<SimpleAccount>

    @Query("select * from account where id =:accountId")
    fun getDetailsById(accountId:Int):AccountWithCateAndExtras
}