package com.jscoolstar.accountremeber.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Administrator on 2018/3/30.
 */
class DBHelper : SQLiteOpenHelper {
    companion object {
        val dataName = "account.db"
        val dataversion = 1
        val firstVersion = 1
        var instance:DBHelper?=null

        @Synchronized
        fun getInstance(context: Context):DBHelper{
            if(instance==null){
                instance = DBHelper(context)
            }
            return instance!!
        }
    }

    constructor(context: Context) : super(context, dataName, null, dataversion)

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        for(i in oldVersion until  newVersion){
            when(i){
                1 ->{
                    upgradeToVersion2(db)
                }
            }
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL.CREATE_T_ACCOUNT)
        db?.execSQL(SQL.CREATE_T_EXTRA_COLUMN)
        onUpgrade(db, firstVersion, dataversion)
    }

    private fun upgradeToVersion2(db:SQLiteDatabase?){
        //升级数据库时参考此模式
    }
}