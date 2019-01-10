package com.jscoolstar.accountremeber.utils

import android.database.Cursor
import java.lang.Exception
import java.lang.reflect.ParameterizedType

inline fun <reified T> Cursor.get(key: String): T {


//    val entityClass : Class<T> = ((javaClass.genericSuperclass) as ParameterizedType).actualTypeArguments[0] as Class<T>
    val entityClass = T::class.java

    var index = getColumnIndex(key)
    var result: Any?=null

    result = when (entityClass) {
        String::class.java -> getString(index)
        java.lang.String::class.java -> getString(index)
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
        else -> null
    }
    if (result == null) {
        throw Exception("not support class T")
    }
    return result as T
}
