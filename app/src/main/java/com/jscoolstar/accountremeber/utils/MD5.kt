package com.jscoolstar.accountremeber.utils

import java.io.UnsupportedEncodingException
import java.security.MessageDigest

object MD5 {
    @Throws(UnsupportedEncodingException::class)
    fun getMD5(sourceStr: String): String {
        val toChapter = sourceStr.toByteArray(charset("UTF-8"))
        val md5str = StringBuffer()
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(toChapter)
            val toChapterDigest = md5.digest()
            var digital: Int
            for (i in toChapterDigest.indices) {
                digital = toChapterDigest[i].toInt()
                if (digital < 0) {
                    // 将8位字节之前的高位全变为0
                    digital += 256
                }
                if (digital < 16) {
                    md5str.append("0")
                }
                md5str.append(Integer.toHexString(digital).toUpperCase())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return md5str.toString()
    }
}