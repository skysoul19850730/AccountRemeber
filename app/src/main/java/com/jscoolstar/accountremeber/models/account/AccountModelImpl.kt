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
            cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, null, null, null, null, null)
            if (cursor != null) {
                var column: ExtraColumn
                while (cursor.moveToNext()) {
                    var account: Account = cursorToAccount(cursor)
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

    override fun addAccount(account: Account): Boolean {
        var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
        try {
            dbHelper.writableDatabase.beginTransaction()
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
            dbHelper.writableDatabase.delete(SQL.ACCOUNT.TABLENAME, "id=?", arrayOf(account.id.toString()))
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
            cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, SQL.ACCOUNT.DB1.C_PLATFORM + " like ?", arrayOf("% ${key} %"), null, null, null)
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
        contentValues.put(SQL.ACCOUNT.DB1.C_PLATFORM, account.platform)
        contentValues.put(SQL.ACCOUNT.DB1.C_password, account.password)
        contentValues.put(SQL.ACCOUNT.DB1.C_tip, account.tip)
        contentValues.put(SQL.ACCOUNT.DB1.C_bindphone, account.bindphone)
        contentValues.put(SQL.ACCOUNT.DB1.C_bindmail, account.bindmail)
        contentValues.put(SQL.ACCOUNT.DB1.C_create_time, account.create_time)
        contentValues.put(SQL.ACCOUNT.DB2.C_ACCOUNT,account.accountName)
        contentValues.put(SQL.ACCOUNT.DB2.C_CATEID,account.cate?.id)
        return contentValues
    }

    private fun cursorToAccount(cursor: Cursor): Account {
        var account: Account = Account()
        account.id = cursor.getInt(cursor.getColumnIndex("id"))
        account.bindmail = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB1.C_bindmail))
        account.bindphone = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB1.C_bindphone))
        account.platform = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB1.C_PLATFORM))
        account.password = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB1.C_password))
        account.tip = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB1.C_tip))
        account.accountName = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB2.C_ACCOUNT))
        account.create_time = cursor.getString(cursor.getColumnIndex(SQL.ACCOUNT.DB1.C_create_time))
        account.extraColumnList = ExtraColumnModelImpl().getColumnsWithAccountID(account.id)
        var cateID = cursor.getInt(cursor.getColumnIndex(SQL.ACCOUNT.DB2.C_CATEID))
        account.cate = CateModelImpl().getCateByID(cateID)
        return account
    }
}