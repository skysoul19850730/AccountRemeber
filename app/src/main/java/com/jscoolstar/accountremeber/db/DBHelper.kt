package com.jscoolstar.accountremeber.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Created by Administrator on 2018/3/30.
 */
class DBHelper : SQLiteOpenHelper {
    companion object {
        val dataName = "account.db"
        val dataversion = 2
        val firstVersion = 1 // this version should not be changed
        var instance: DBHelper? = null

        @Synchronized
        fun getInstance(context: Context): DBHelper {
            if (instance == null) {
                instance = DBHelper(context)
            }
            return instance!!
        }
    }

    constructor(context: Context) : super(context, dataName, null, dataversion)

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        for (i in oldVersion until newVersion) {
            when (i) {
                1 -> {
                    upgradeToVersion2(db)
                }
            }
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL.ACCOUNT.DB1.SQL_CREATE_TABLE)
        db?.execSQL(SQL.EXTRA.DB1.SQL_CREATE_TABLE)
        onUpgrade(db, firstVersion, dataversion)
    }

    private fun upgradeToVersion2(db: SQLiteDatabase?) {
        Log.d("sqc","upgradeToVersion2")
        //升级数据库时参考此模式
        // new table named "cate"
        db?.execSQL(SQL.CATE.DB2.SQL_CREATE_TABLE)
        // add a row with "default" in table cate
        var contentValues: ContentValues = ContentValues()
        contentValues.put(SQL.CATE.DB2.C_cateName, "default")
        var defaultID = db?.insert(SQL.CATE.TABLENAME, null, contentValues)

        //user table adds 2 columns named 'account' and 'cateid'
        db?.execSQL(SQL.ACCOUNT.DB2.SQL_ADD_COLUMN_ACCOUNT)
        db?.execSQL(SQL.ACCOUNT.DB2.SQL_ADD_COLUMN_CATEID)

        //set all cateid in table account to defaultID
        db?.execSQL("update ${SQL.ACCOUNT.TABLENAME} set ${SQL.ACCOUNT.DB2.C_CATEID} = $defaultID")

    }
}