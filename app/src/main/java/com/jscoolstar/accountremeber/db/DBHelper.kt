package com.jscoolstar.accountremeber.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.jscoolstar.accountremeber.db.entity.DMCate
import com.jscoolstar.accountremeber.models.account.CateModelImpl

/**
 * Created by Administrator on 2018/3/30.
 */
class DBHelper : SQLiteOpenHelper {
    companion object {
        val dataName = "account.db"
        val dataversion = 1
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

    var currentDB: SQLiteDatabase? = null
    var isCreateing = false

    constructor(context: Context) : super(context, dataName, null, dataversion)

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

//        for (i in oldVersion until newVersion) {
//            when (i) {
//                1 -> {
//                    upgradeToVersion2(db)
//                }
//            }
//        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        currentDB = db
        db?.execSQL(SQL.ACCOUNT.DB1.SQL_CREATE_TABLE)
        db?.execSQL(SQL.EXTRA.DB1.SQL_CREATE_TABLE)
        db?.execSQL(SQL.User.DB1.SQL_CREATE_TABLE)
        db?.execSQL(SQL.CATE.DB1.SQL_CREATE_TABLE)

        addDefaultCate()
        onUpgrade(db, firstVersion, dataversion)
    }

    private fun upgradeToVersionDemo(db: SQLiteDatabase?) {
        Log.d("sqc", "upgradeToVersion2")
        //升级数据库时参考此模式
        // new table named "cate"
        // add a row with "default" in table cate
//        var contentValues: ContentValues = ContentValues()
//        contentValues.put(SQL.CATE.DB2.C_cateName, "default")
//        var defaultID = db?.insert(SQL.CATE.TABLENAME, null, contentValues)
//
//        //user table adds 2 columns named 'account' and 'cateid'
//        db?.execSQL(SQL.ACCOUNT.DB2.SQL_ADD_COLUMN_ACCOUNT)
//        db?.execSQL(SQL.ACCOUNT.DB2.SQL_ADD_COLUMN_CATEID)
//
//        //set all cateid in table account to defaultID
//        db?.execSQL("update ${SQL.ACCOUNT.TABLENAME} set ${SQL.ACCOUNT.DB2.C_CATEID} = $defaultID")

    }

    private fun addDefaultCate(){
        var cate = DMCate();
        cate.cateName="default"
        CateModelImpl().addOneCate(cate)
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        if (isCreateing && currentDB != null) {
            return currentDB!!
        }
        return super.getWritableDatabase()
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        if (isCreateing && currentDB != null) {
            return currentDB!!
        }
        return super.getReadableDatabase()
    }

}