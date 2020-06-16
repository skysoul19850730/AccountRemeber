package com.jscoolstar.accountremeber.dbroom.daos

import androidx.room.Dao
import androidx.room.Query
import com.jscoolstar.accountremeber.dbroom.BaseDao
import com.jscoolstar.accountremeber.dbroom.entities.DMCate

@Dao
interface CateDao : BaseDao<DMCate> {

    @Query("select * from cate where userId = :userId")
    fun getAllCaesByUserid(userId: Int): List<DMCate>

    @Query("select * from cate where id = :id")
    fun getCateById(id: Int): DMCate?

    @Query("select * from cate where userId = :userId and cateName = :name")
    fun getCateByName(userId: Int, name: String): DMCate?

}