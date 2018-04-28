package com.jscoolstar.accountremeber.utils

import java.io.File
import java.io.IOException
import java.security.Provider
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * Created by Administrator on 2018/3/29.
 */

class Util {
    object INSTANCE {

        fun formatTime(time: Long): String {
            var format = "yy-MM-dd hh:mm:ss"
            return SimpleDateFormat(format).format(time)
        }
    }


    @Throws(Exception::class)
    fun get(): ByteArray {
        val a = "123".toByteArray()
        val s = "12313123123"
        s[3]
        Integer.valueOf("fsfs", 3)
        return a
    }

    class CryptoProvider : Provider("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)") {
        /**
         * Creates a Provider and puts parameters
         */
        init {
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl")
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software")
        }
    }

    @Throws(IOException::class)
    fun testCreatFile() {
        val folder = "data/data"
        val file = "test.txt"
        val file1 = File(folder, file)
        if (!file1.exists()) {
            file1.createNewFile()
        }
    }
}
