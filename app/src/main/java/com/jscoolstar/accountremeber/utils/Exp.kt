package com.jscoolstar.accountremeber.utils

import android.database.Cursor
import android.util.Log
import com.jscoolstar.accountremeber.apps.MApplication
import java.lang.Exception
import java.lang.reflect.ParameterizedType

fun Any.log(text:String?){
    var tag = javaClass.simpleName
    Log.d(tag,text)
}
fun Any.log(id:Int){
    var tag = javaClass.simpleName
    Log.d(tag,MApplication.getInstance().getContext().getString(id))
}
fun Any.logE(text:String?){
    var tag = javaClass.simpleName
    Log.e(tag,text)
}
fun Any.logE(id:Int){
    var tag = javaClass.simpleName
    Log.e(tag,MApplication.getInstance().getContext().getString(id))
}


inline fun <reified T> Cursor.get(key: String): T {


//    val entityClass : Class<T> = ((javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<T>
    val entityClass = T::class.java

    var index = getColumnIndex(key)
    var result: Any?=null

    result = when (entityClass) {
        String::class.java -> getStringNotNull(index)
        java.lang.String::class.java -> getStringNotNull(index)
        Long::class.java -> getLong(index)
        java.lang.Long::class.java -> getLong(index)
        Double::class.java -> getDouble(index)
        java.lang.Double::class.java -> getDouble(index)
        Float::class.java -> getFloat(index)
        java.lang.Float::class.java -> getFloat(index)
        Int::class.javaObjectType -> getInt(index)
        Integer::class.java -> getInt(index)
        Boolean::class.java -> getInt(index)>0
        java.lang.Boolean::class.java->getInt(index)>0
        else ->  {
            throw Exception("not support class T")
        }
    }
    return result as T
}
fun Cursor.getStringNotNull(columnIndex:Int):String{
    var result = getString(columnIndex)
    if(result==null)return ""
    return result
}