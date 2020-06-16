package com.jscoolstar.accountremeber.dbroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jscoolstar.accountremeber.dbroom.daos.AccountDao
import com.jscoolstar.accountremeber.dbroom.daos.CateDao
import com.jscoolstar.accountremeber.dbroom.daos.ExtrasDao
import com.jscoolstar.accountremeber.dbroom.daos.UserDao
import com.jscoolstar.accountremeber.dbroom.entities.DMAccount
import com.jscoolstar.accountremeber.dbroom.entities.DMCate
import com.jscoolstar.accountremeber.dbroom.entities.DMExtraColumn
import com.jscoolstar.accountremeber.dbroom.entities.DMUser

@Database(version = 1,exportSchema = false,
        entities = arrayOf(DMAccount::class,DMCate::class,DMUser::class,DMExtraColumn::class))
abstract class AccountDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun cateDao(): CateDao
    abstract fun accountDao():AccountDao
    abstract fun extrasDao():ExtrasDao

    companion object{
        private var INSTANCE:AccountDatabase?=null
        var lock = Any()
        fun getInstance(context: Context):AccountDatabase{
            if(INSTANCE==null){
                synchronized(lock){
                    if(INSTANCE==null){
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AccountDatabase::class.java,"account2.db").build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}