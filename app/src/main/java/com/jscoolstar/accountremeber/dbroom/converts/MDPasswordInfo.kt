package com.jscoolstar.accountremeber.dbroom.converts

import android.text.TextUtils
import com.jscoolstar.accountremeber.utils.MD5

 class MDPasswordInfo(var password: String = "") {

    //是否通过new构造的。防止数据库取出的text重复convert
    var isNew = true

    fun hasValue() = !TextUtils.isEmpty(password)

    fun isPasswordCorrect(passwordInputed:String):Boolean{
        return password == MD5.getMD5(passwordInputed)
    }

}