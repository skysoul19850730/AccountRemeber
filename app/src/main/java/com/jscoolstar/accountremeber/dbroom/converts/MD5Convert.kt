package com.jscoolstar.accountremeber.dbroom.converts

import android.text.TextUtils
import androidx.room.TypeConverter
import com.jscoolstar.accountremeber.utils.MD5

class MD5Convert {
    companion object{

        @TypeConverter
        @JvmStatic
        fun toPasswordString(info:MDPasswordInfo?):String{
            if(info==null || !info.hasValue())return ""
            return if(info.isNew) MD5.getMD5(info.password) else info.password
        }

        @TypeConverter
        @JvmStatic
        fun toMDPassword(str:String?):MDPasswordInfo?{
            if(TextUtils.isEmpty(str))return null
            var result = MDPasswordInfo()
            result.isNew = false//转化时赋值false，再次插入或update避免重复md5
            result.password = str!!
            return result
        }
    }
}