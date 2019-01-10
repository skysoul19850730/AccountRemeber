package com.jscoolstar.accountremeber.models.account

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.DMExtraColumn
import com.jscoolstar.accountremeber.utils.get

/**
 * Created by Administrator on 2018/4/2.
 */
class ExtraColumnModelImpl : ExtraColumnModel {
    var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
    val TName = SQL.EXTRA.TABLENAME
    var db1 = SQL.EXTRA.DB1;

    private fun toExtraColumn(cursor: Cursor): DMExtraColumn {
        var column = DMExtraColumn()
        column.id = cursor.get("id")
        column.aId = cursor.get(db1.C_aid)
        column.key = cursor.get(db1.C_key)
        column.value = cursor.get(db1.C_value)
        return column
    }
    private fun getContentValues(column: DMExtraColumn): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(db1.C_key, column.key)
        contentValues.put(db1.C_value, column.value)
        contentValues.put(db1.C_aid, column.aId)
        if (column.id > 0) {
            contentValues.put("id", column.id)
        }
        return contentValues
    }


    override fun getColumnsWithAccountID(accountId: Int): ArrayList<DMExtraColumn> {
        var columns = ArrayList<DMExtraColumn>()
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(TName, null, "${db1.C_aid}=?", arrayOf("" + accountId), null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var column = toExtraColumn(cursor)
                    columns.add(column)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            cursor?.close()
            throw e
        }
        return columns
    }

    override fun deleteColumnWithId(columnId: Int): Boolean {
        try {
            dbHelper.writableDatabase.delete(TName, "id=?", arrayOf("" + columnId))

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun deleteColumns4AccoutnId(accountId: Int): Boolean {
        try {
            dbHelper.writableDatabase.delete(TName, SQL.EXTRA.DB1.C_aid + "=?", arrayOf("" + accountId))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    @Throws(Exception::class)
    override fun addColumn4AccountId(accountId: Int, column: DMExtraColumn): Boolean {
        try {
            column.aId = accountId
            var contentValues = getContentValues(column)
            if (column.id > 0) {
                //代表是修改
                editColumn(column)
            } else
                dbHelper.writableDatabase.insert(TName, null, contentValues)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
            return false
        }
        return true
    }


    override fun addColumns4AccountId(accountId: Int, columns: List<DMExtraColumn>): Boolean {
        var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
        try {

            dbHelper.writableDatabase.beginTransaction()
            for (column in columns) {
                addColumn4AccountId(accountId, column)
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

    private fun editColumn(column: DMExtraColumn): Boolean {
        try {
            var contentValues = getContentValues(column)
            dbHelper.writableDatabase.update(TName, contentValues, "id=?", arrayOf(column.id.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


}