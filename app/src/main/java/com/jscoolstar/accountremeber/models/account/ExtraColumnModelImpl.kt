package com.jscoolstar.accountremeber.models.account

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.ExtraColumn

/**
 * Created by Administrator on 2018/4/2.
 */
class ExtraColumnModelImpl : ExtraColumnModel {
    override fun getColumnsWithAccountID(accountId: Int): ArrayList<ExtraColumn> {
        var columns = ArrayList<ExtraColumn>()
        var cursor: Cursor? = null
        try {


            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            cursor = dbHelper.readableDatabase.query(SQL.T_EXTRA_COLUMN, null, "aid=?", arrayOf("" + accountId), null, null, null)

            if (cursor != null) {
                var column: ExtraColumn
                while (cursor.moveToNext()) {
                    column = ExtraColumn()
                    column.id = cursor.getInt(cursor.getColumnIndex("id"))
                    column.aId = accountId
                    column.key = cursor.getString(cursor.getColumnIndex(SQL.TE_key))
                    column.value = cursor.getString(cursor.getColumnIndex(SQL.TE_value))
                    columns.add(column)
                }
                cursor.close()
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

            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            dbHelper.writableDatabase.delete(SQL.T_EXTRA_COLUMN, "id=?", arrayOf("" + columnId))

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    override fun deleteColumns4AccoutnId(accountId: Int): Boolean {
        try {
            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            dbHelper.writableDatabase.delete(SQL.T_EXTRA_COLUMN, SQL.TE_aid + "=?", arrayOf("" + accountId))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    @Throws(Exception::class)
    override fun addColumn4AccountId(accountId: Int, column: ExtraColumn): Boolean {
        try {

            column.aId = accountId
            var contentValues = getContentValueWithColumn(column)
            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            if (column.id > 0) {
                //代表是修改
                editColumn(column)
            } else
                dbHelper.writableDatabase.insert(SQL.T_EXTRA_COLUMN, null, contentValues)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
            return false
        }
        return true
    }


    override fun addColumns4AccountId(accountId: Int, columns: List<ExtraColumn>): Boolean {
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

    private fun editColumn(column: ExtraColumn): Boolean {
        try {
            var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
            var contentValues = getContentValueWithColumn(column)
            dbHelper.writableDatabase.update(SQL.T_EXTRA_COLUMN, contentValues, "id=?", arrayOf(column.id.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun getContentValueWithColumn(column: ExtraColumn): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(SQL.TE_key, column.key)
        contentValues.put(SQL.TE_value, column.value)
        contentValues.put(SQL.TE_aid, column.aId)
        if (column.id > 0) {
            contentValues.put("id", column.id)
        }
        return contentValues
    }
}