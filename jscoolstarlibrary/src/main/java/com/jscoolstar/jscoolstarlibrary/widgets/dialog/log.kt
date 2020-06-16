package com.jscoolstar.jscoolstarlibrary.widgets.dialog

import android.content.Context


fun Any.isInt():Boolean{
    var cl = javaClass
    when(cl){
        Int::class.javaObjectType -> return true
        Integer::class.java -> return true
        else ->return false
    }
}
fun Any.isString():Boolean{
    var cl = javaClass
    when(cl){
        java.lang.String::class.java -> return true
        String::class.java -> return true
        else ->return false
    }
}

fun Any.getString(context: Context):String{
    if(isInt()){
        return context.getString(this as Int)
    }
    if(isString())return this as String
    return ""
}
