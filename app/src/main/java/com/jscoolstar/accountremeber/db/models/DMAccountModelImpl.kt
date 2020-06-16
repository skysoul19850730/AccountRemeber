package com.jscoolstar.accountremeber.db.models

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.utils.get

class DMAccountModelImpl() : DMAccountModel {

    var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
    val TName = SQL.ACCOUNT.TABLENAME
    var db1 = SQL.ACCOUNT.DB1;


    private fun toAccount(cursor: Cursor): DMAccount {
        var account = DMAccount()
        account.id = cursor.get("id")
        account.bindmail = cursor.get(db1.C_bindmail)
        account.bindphone = cursor.get(db1.C_bindphone)
        account.platform = cursor.get(db1.C_PLATFORM)
        account.password = cursor.get(db1.C_password)
        account.tip = cursor.get(db1.C_tip)
        account.accountName = cursor.get(db1.C_ACCOUNT)
        account.create_time = cursor.get(db1.C_create_time)
        account.cateId = cursor.get(db1.C_CATEID)
        account.userId = cursor.get(db1.C_UserID)
        return account
    }

    private fun getCountentValues(account: DMAccount): ContentValues {

        var contentValues = ContentValues()
        contentValues.put(db1.C_PLATFORM, account.platform)
        contentValues.put(db1.C_password, account.password)
        contentValues.put(db1.C_tip, account.tip)
        contentValues.put(db1.C_bindphone, account.bindphone)
        contentValues.put(db1.C_bindmail, account.bindmail)
        contentValues.put(db1.C_create_time, account.create_time)
        contentValues.put(db1.C_ACCOUNT, account.accountName)
        contentValues.put(db1.C_UserID, account.userId)
        contentValues.put(db1.C_CATEID, account.cateId)
        return contentValues
    }

    override fun getAccountListAll(userId: Int, cateId: Int): List<DMAccount> {
        var accounts = ArrayList<DMAccount>()
        if (userId <= 0) {
            return accounts
        }
        var cursor: Cursor? = null
        try {
            if (cateId <= 0)
                cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, "${db1.C_UserID} = ?", arrayOf(userId.toString()), null, null, null)
            else
                cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, "${db1.C_UserID} = ? AND ${db1.C_CATEID} = ?", arrayOf(userId.toString(),cateId.toString()), null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var account: DMAccount = toAccount(cursor)
                    accounts.add(account)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return accounts
    }

    override fun addAccount(account: DMAccount): Boolean {
        try {
            var contentValues = getCountentValues(account)

            if (account.id > 0) {
                dbHelper.writableDatabase.update(SQL.ACCOUNT.TABLENAME, contentValues, "id=?", arrayOf(account.id.toString()))
            } else {
                var id = dbHelper.writableDatabase.insert(SQL.ACCOUNT.TABLENAME, null, contentValues)
                if (id < 0) {
                    throw Exception()
                }
                account.id = id.toInt()
            }
//            if (account.extraColumnList != null) {
//                extraColumnModel.addColumns4AccountId(account.id, account.extraColumnList!!)
//            }
//            dbHelper.writableDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
//            dbHelper.writableDatabase.endTransaction()
        }
        return true
    }

    override fun deleteAccount(account: DMAccount): Boolean {
        try {
            dbHelper.writableDatabase.delete(SQL.ACCOUNT.TABLENAME, "id=?", arrayOf(account.id.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
        }
        return true
    }

    override fun getAccountListWithKeyWord(key: String, userId: Int): List<DMAccount> {
        var accounts = ArrayList<DMAccount>()
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, "${db1.C_PLATFORM} like ? AND ${db1.C_UserID}=?", arrayOf("% ${key} %", userId.toString()), null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var account: DMAccount = toAccount(cursor)
                    accounts.add(account)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            cursor?.close()
        }

        return accounts
    }
}