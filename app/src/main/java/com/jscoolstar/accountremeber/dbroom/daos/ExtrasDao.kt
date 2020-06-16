package com.jscoolstar.accountremeber.dbroom.daos

import androidx.room.Dao
import androidx.room.Query
import com.jscoolstar.accountremeber.dbroom.BaseDao
import com.jscoolstar.accountremeber.dbroom.entities.DMExtraColumn

@Dao
interface ExtrasDao:BaseDao<DMExtraColumn> {

    @Query("select * from extra_column where accountId = :accountId")
    fun getExtrasByAccountId(accountId:Int):List<DMExtraColumn>
}