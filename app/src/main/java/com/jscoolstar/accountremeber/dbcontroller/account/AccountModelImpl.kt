package com.jscoolstar.accountremeber.dbcontroller.account

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.DMAccount
import com.jscoolstar.accountremeber.db.entity.DMExtraColumn
import com.jscoolstar.accountremeber.utils.get

/**
 * Created by Administrator on 2018/4/11.
 */
class AccountModelImpl : DCAccountModel {

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
        account.extraColumnList = ExtraColumnModelImpl().getColumnsWithAccountID(account.id)
        var cateID: Int = cursor.get(db1.C_CATEID)
        account.cate = CateModelImpl().getCateByID(cateID)
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
        contentValues.put(db1.C_CATEID, account.cate?.id)
        return contentValues
    }


    val extraColumnModel = ExtraColumnModelImpl()

    override fun getAccountListAll(): List<DMAccount> {
        var accounts = ArrayList<DMAccount>()
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, null, null, null, null, null)
            if (cursor != null) {
                var column: DMExtraColumn
                while (cursor.moveToNext()) {
                    var account: DMAccount = cursorToAccount(cursor)
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

    override fun deleteAccount(account: DMAccount): Boolean {
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


    override fun getAccountListWithKeyWord(key: String): List<DMAccount> {
        var accounts = ArrayList<DMAccount>()
        var cursor: Cursor? = null
        try {
            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            cursor = dbHelper.readableDatabase.query(SQL.ACCOUNT.TABLENAME, null, db1.C_PLATFORM + " like ?", arrayOf("% ${key} %"), null, null, null)
            if (cursor != null) {
                var column: DMExtraColumn
                while (cursor.moveToNext()) {
                    var account: DMAccount = cursorToAccount(cursor)
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