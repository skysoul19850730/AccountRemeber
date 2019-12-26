package com.jscoolstar.accountremeber.utils

import android.os.Build
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.security.Provider
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by Administrator on 2018/3/29.
 */
internal object AESUtil {
    val mSeed = "850730test"
    private class CryptoProvider : Provider("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)") {
        init {
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl")
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

    fun generateKey(): String {
        try {
            var localSecureRandom = SecureRandom.getInstance("SHA1PRNG")
            var bytes_key = ByteArray(20)
            localSecureRandom.nextBytes(bytes_key)
            return toHex(bytes_key)
        } catch (e: Exception) {

        }
        return ""
    }

    fun encrypt(seed: String, content: String): String{

        var result = encrypt2Byte(seed, content)

        return toHex(result)
    }

    fun decrypt(seed: String, content: String): String {
        var specKey:SecretKeySpec?=null
        if(Build.VERSION.SDK_INT>=28){
            specKey = SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(seed.toByteArray(StandardCharsets.US_ASCII),32),"AES")
        }else{
            var rawKey = getRawKey(seed.toByteArray())
            specKey = SecretKeySpec(rawKey,"AES")
        }

        var content2 = toByte(content)
        var result = decrypt(content2, specKey)
        return result.toString(Charset.forName("utf-8"))
    }

    private fun encrypt2Byte(seed: String, content: String): ByteArray {
        var specKey:SecretKeySpec?=null
        if(Build.VERSION.SDK_INT>=28){
            specKey = SecretKeySpec(InsecureSHA1PRNGKeyDerivator.deriveInsecureKey(seed.toByteArray(StandardCharsets.US_ASCII),32),"AES")
        }else{
            var rawKey = getRawKey(seed.toByteArray())
            specKey = SecretKeySpec(rawKey,"AES")
        }

        return encrypt(content.toByteArray(Charset.forName("utf-8")), specKey)
    }

    @Throws(Exception::class)
    private fun decrypt(byteData: ByteArray, keySpec:SecretKeySpec): ByteArray {
        return Aes(byteData, keySpec, Cipher.DECRYPT_MODE)
    }

    @Throws(Exception::class)
    private fun encrypt(byteData: ByteArray, keySpec:SecretKeySpec): ByteArray {
        return Aes(byteData, keySpec, Cipher.ENCRYPT_MODE)
    }

    @Throws(Exception::class)
    private fun Aes(byteData: ByteArray,keySpec:SecretKeySpec, opmode: Int): ByteArray {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(opmode, keySpec)
        return cipher.doFinal(byteData)
    }


    @Throws(Exception::class)
    private fun getRawKey(seed: ByteArray): ByteArray {

        val kgen = KeyGenerator.getInstance("AES")
        var sr: SecureRandom? = null
        val sdk_version = Build.VERSION.SDK_INT
        sr = when {
            sdk_version > 23 -> SecureRandom.getInstance("SHA1PRNG", CryptoProvider())
            sdk_version >= 17 -> SecureRandom.getInstance("SHA1PRNG", "Crypto")
            else -> SecureRandom.getInstance("SHA1PRNG")
        }
        sr.setSeed(seed)
        kgen.init(128, sr)
        var skey: SecretKey = kgen.generateKey()
        return skey.encoded
    }


    private fun toHex(buf: ByteArray): String {
        val HEX: String = "0123456789ABCDEF"
        if (buf == null) return ""
        var result: StringBuffer = StringBuffer(2 * buf.size)
        for (c in buf) {

            result.append(HEX[(c.toInt() shr 4) and 0x0f]).append(HEX[c.toInt() and 0x0f])
        }
        return result.toString()
    }

    private fun toByte(hexString: String): ByteArray {
        val length = hexString.length / 2
        var result = ByteArray(length)
        for (i in 0..length - 1) {

            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).toByte()
        }
        return result
    }

}