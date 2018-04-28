package com.jscoolstar.accountremeber.models.account

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.Account
import com.jscoolstar.accountremeber.db.entity.ExtraColumn

/**
 * Created by Administrator on 2018/4/11.
 */
class AccountModelImpl : AccountModel {
    val extraColumnModel = ExtraColumnModelImpl()

    override fun getAccountListAll(): List<Account> {
        var accounts = ArrayList<Account>()
        var cursor: Cursor? = null
        try {
            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            cursor = dbHelper.readableDatabase.query(SQL.T_ACCOUNT, null, null, null, null, null, null)
            if (cursor != null) {
                var column: ExtraColumn
                while (cursor.moveToNext()) {
                    var account: Account = cursorToAccount(cursor)
                    accounts.add(account)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            cursor?.close()
        }

        return accounts
    }

    override fun addAccount(account: Account): Boolean {
        var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
        try {
            dbHelper.writableDatabase.beginTransaction()
            var contentValues = getCountentValues(account)

            if (account.id > 0) {
                dbHelper.writableDatabase.update(SQL.T_ACCOUNT, contentValues, "id=?", arrayOf(account.id.toString()))
            } else {
                var id = dbHelper.writableDatabase.insert(SQL.T_ACCOUNT, null, contentValues)
                if (id < 0) {
                    throw Exception()
                }
                account.id = id.toInt()
            }
            if (account.extraColumnList != null) {
                extraColumnModel.addColumns4AccountId(account.id, account.extraColumnList!!)
            }
            dbHelper.writableDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            dbHelper.writableDatabase.endTransaction()
        }
        return true
    }

    override fun deleteAccount(account: Account): Boolean {
        var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
        try {
            dbHelper.writableDatabase.beginTransaction()
            extraColumnModel.deleteColumns4AccoutnId(account.id)//先删除账户的额外属性，然后再删除账户
            dbHelper.writableDatabase.delete(SQL.T_ACCOUNT, "id=?", arrayOf(account.id.toString()))
            dbHelper.writableDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            dbHelper.writableDatabase.endTransaction()
        }
        return true
    }


    override fun getAccountListWithKeyWord(key: String): List<Account> {
        var accounts = ArrayList<Account>()
        var cursor: Cursor? = null
        try {
            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            cursor = dbHelper.readableDatabase.query(SQL.T_ACCOUNT, null, SQL.TA_PLATFORM + " like ?", arrayOf("% ${key} %"), null, null, null)
            if (cursor != null) {
                var column: ExtraColumn
                while (cursor.moveToNext()) {
                    var account: Account = cursorToAccount(cursor)
                    accounts.add(account)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            cursor?.close()
        }

        return accounts
    }

    private fun getCountentValues(account: Account): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(SQL.TA_PLATFORM,account.platform)
        contentValues.put(SQL.TA_password,account.password)
        contentValues.put(SQL.TA_tip,account.tip)
        contentValues.put(SQL.TA_bindphone,account.bindphone)
        contentValues.put(SQL.TA_bindmail,account.bindmail)
        contentValues.put(SQL.TA_create_time,account.create_time)
        return contentValues
    }

    private fun cursorToAccount(cursor: Cursor): Account {
        var account: Account = Account()
        account.id = cursor.getInt(cursor.getColumnIndex("id"))
        account.bindmail = cursor.getString(cursor.getColumnIndex(SQL.TA_bindmail))
        account.bindphone = cursor.getString(cursor.getColumnIndex(SQL.TA_bindphone))
        account.platform = cursor.getString(cursor.getColumnIndex(SQL.TA_PLATFORM))
        account.password = cursor.getString(cursor.getColumnIndex(SQL.TA_password))
        account.tip = cursor.getString(cursor.getColumnIndex(SQL.TA_tip))
        account.create_time = cursor.getString(cursor.getColumnIndex(SQL.TA_create_time))
        account.extraColumnList = ExtraColumnModelImpl().getColumnsWithAccountID(account.id)
        return account
    }
}