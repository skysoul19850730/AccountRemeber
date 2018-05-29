package com.jscoolstar.accountremeber.models.account

import android.content.ContentValues
import android.database.Cursor
import com.jscoolstar.accountremeber.apps.MApplication
import com.jscoolstar.accountremeber.db.DBHelper
import com.jscoolstar.accountremeber.db.SQL
import com.jscoolstar.accountremeber.db.entity.Cate

class CateModelImpl :CateModel {
    override fun getCateByID(id: Int): Cate? {
        var cate:Cate?=null
        var cursor:Cursor?=null
        try {
            var dbHelper:DBHelper= DBHelper.getInstance(MApplication.getInstance().getContext())
            cursor = dbHelper.readableDatabase.query(SQL.CATE.TABLENAME,null,"id = ?", arrayOf(id.toString()),null,null,null)
            if(cursor!=null){

                while (cursor.moveToNext()){
                    cate = Cate()
                    cate.id = cursor.getInt(cursor.getColumnIndex("id"))
                    cate.cateName = cursor.getString(cursor.getColumnIndex(SQL.CATE.DB2.C_cateName))
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }
        return cate
    }

    override fun getAllCates(): List<Cate> {
        var cates = ArrayList<Cate>()
        var cursor:Cursor?=null
        try {
            var dbHelper:DBHelper= DBHelper.getInstance(MApplication.getInstance().getContext())
            cursor = dbHelper.readableDatabase.query(SQL.CATE.TABLENAME,null,null,null,null,null,null)
            if(cursor!=null){

                while (cursor.moveToNext()){
                    var cate:Cate = Cate()
                    cate.id = cursor.getInt(cursor.getColumnIndex("id"))
                    cate.cateName = cursor.getString(cursor.getColumnIndex(SQL.CATE.DB2.C_cateName))
                    cates.add(cate)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            cursor?.close()
        }
        return cates
    }

    override fun addOneCate(cate: Cate):Boolean {
        var dbHelper: DBHelper = DBHelper.getInstance(MApplication.getInstance().getContext());
        try {

            var cursor = dbHelper.writableDatabase.query(SQL.CATE.TABLENAME,null," ${SQL.CATE.DB2.C_cateName}=?", arrayOf(cate.cateName),null,null,null)
            while (cursor.moveToNext()){
                cursor.close()
                return false
            }


            var contentValues = ContentValues()
            contentValues.put(SQL.CATE.DB2.C_cateName,cate.cateName)
            if(cate.id>0){
                dbHelper.writableDatabase.update(SQL.CATE.TABLENAME,contentValues,"id=?", arrayOf(cate.id.toString()))
            }else{
                var id = dbHelper.writableDatabase.insert(SQL.CATE.TABLENAME,null,contentValues)
                if(id<0){
                    throw Exception()
                }
                cate.id = id.toInt();
            }
        }catch (e:Exception){
            e.printStackTrace()
            return false
        }finally {

        }

        return true
    }
}