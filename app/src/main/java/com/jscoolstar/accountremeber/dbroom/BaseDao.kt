package com.jscoolstar.accountremeber.dbroom

import androidx.room.*

@Dao
interface BaseDao<T> {

    @Insert
    fun insert(t: T): Long

    @Insert
    fun insert(list: List<T>): LongArray

    @Delete
    fun delete(t: T): Int

    @Delete
    fun delete(list: List<T>)

    @Update
    fun update(t: T): Int

    @Update
    fun update(list: List<T>)

}