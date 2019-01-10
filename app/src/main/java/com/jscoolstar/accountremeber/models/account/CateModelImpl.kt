package com.jscoolstar.accountremeber.models.account

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.DMCate
import com.jscoolstar.accountremeber.utils.get

class CateModelImpl : CateModel {
    var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
    val TName = SQL.CATE.TABLENAME
    var db1 = SQL.CATE.DB1;

    private fun toCate(cursor: Cursor): DMCate {
        var cate = DMCate()
        cate.id = cursor.get("id")
        cate.cateName = cursor.get(db1.C_cateName)
        return cate
    }
    private fun getCountentValues(cate:DMCate):ContentValues{
        var contentValues = ContentValues()
        contentValues.put(db1.C_cateName, cate.cateName)
        return contentValues
    }

    override fun getCateByID(id: Int): DMCate? {
        var cate: DMCate? = null
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(TName, null, "id = ?", arrayOf(id.toString()), null, null, null)
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    cate = toCate(cursor)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return cate
    }

    override fun getAllCates(): List<DMCate> {
        var cates = ArrayList<DMCate>()
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(TName, null, null, null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    var cate = toCate(cursor)
                    cates.add(cate)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return cates
    }

    override fun addOneCate(cate: DMCate): Boolean {
        var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
        try {
            var cursor = dbHelper.writableDatabase.query(TName, null, " ${db1.C_cateName}=?", arrayOf(cate.cateName), null, null, null)
            while (cursor.moveToNext()) {
                cursor.close()
                return false
            }
            var contentValues = getCountentValues(cate)
            if (cate.id > 0) {
                dbHelper.writableDatabase.update(TName, contentValues, "id=?", arrayOf(cate.id.toString()))
            } else {
                var id = dbHelper.writableDatabase.insert(TName, null, contentValues)
                if (id < 0) {
                    throw Exception()
                }
                cate.id = id.toInt();
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {

        }

        return true
    }
}