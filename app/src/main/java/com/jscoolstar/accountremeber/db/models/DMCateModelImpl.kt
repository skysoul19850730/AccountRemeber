package com.jscoolstar.accountremeber.db.models

import android.content.ContentValues
import android.database.Cursor
import android.text.TextUtils
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.DMCate
import com.jscoolstar.accountremeber.utils.get

class DMCateModelImpl() : DMCateModel {
    var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
    val TName = SQL.CATE.TABLENAME
    var db1 = SQL.CATE.DB1;

    private fun toCate(cursor: Cursor): DMCate {
        var cate = DMCate()
        cate.id = cursor.get("id")
        cate.cateName = cursor.get(db1.C_cateName)
        cate.userId = cursor.get(db1.C_userId)
        return cate
    }

    private fun getCountentValues(cate: DMCate): ContentValues {
        var contentValues = ContentValues()
        contentValues.put(db1.C_cateName, cate.cateName)
        contentValues.put(db1.C_userId, cate.userId)
        return contentValues
    }

    override fun getAllCaesByUserid(userId: Int): List<DMCate> {
        var cates = ArrayList<DMCate>()
        var cursor: Cursor? = null
        try {
            cursor = dbHelper.readableDatabase.query(TName, null, "${db1.C_userId} = ?", arrayOf(userId.toString()), null, null, null)
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


    override fun addCate(cate: DMCate): Boolean {
        if (cate.userId == 0 || TextUtils.isEmpty(cate.cateName)) {
            return false
        }
        try {
            var cursor = dbHelper.writableDatabase.query(TName, null, " ${db1.C_cateName}=? AND ${db1.C_userId}=?", arrayOf(cate.cateName, cate.userId.toString()), null, null, null)
            while (cursor.moveToNext()) {
                cursor.close()
                return false
            }
            var contentValues = getCountentValues(cate)
            var id = dbHelper.writableDatabase.insert(TName, null, contentValues)
            if (id < 0) {
                throw Exception()
            }
            cate.id = id.toInt();
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {

        }

        return true
    }

    override fun updateCate(cate: DMCate): Boolean {
        if (cate.userId == 0 || TextUtils.isEmpty(cate.cateName)) {
            return false
        }
        try {
            var cursor = dbHelper.writableDatabase.query(TName, null, " ${db1.C_cateName}=? AND ${db1.C_userId}=? AND id != ?", arrayOf(cate.cateName, cate.userId.toString(), cate.id.toString()), null, null, null)
            while (cursor.moveToNext()) {
                cursor.close()
                return false
            }
            var contentValues = getCountentValues(cate)
            dbHelper.writableDatabase.update(TName, contentValues, "id=?", arrayOf(cate.id.toString()))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {

        }

        return true
    }
    override fun getCateByName(userId: Int, name: String): DMCate? {
        if (userId == 0 || TextUtils.isEmpty(name)) {
            return null
        }
        var cate: DMCate? = null
        var cursor: Cursor? = null
        try {
            var cursor = dbHelper.writableDatabase.query(TName, null, " ${db1.C_cateName}=? AND ${db1.C_userId}=?", arrayOf(name, userId.toString()), null, null, null)
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
    override fun getCateById(id: Int): DMCate? {
        if (id <= 0) return null
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
}