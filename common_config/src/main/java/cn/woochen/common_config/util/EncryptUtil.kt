package cn.woochen.common_config.util

import java.security.MessageDigest


/**
 * 加密解密工具类
 * @author woochen
 * @time 2017/8/24 10:21
 * @desc 功能简述: 加密解密工具类，对MD5/BASE64/DES/RSA等算法提供了包装.
 */

object EncryptUtil {
    var hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    private fun encryptMD5(originString: String?): String {
        if (originString != null) {
            try {
                val md5 = MessageDigest.getInstance("MD5")
                val results = md5.digest(originString.toByteArray())
                return byteArrayToHexString(results)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return ""
    }


    private fun byteArrayToHexString(b: ByteArray): String {
        val resultSb = StringBuffer()
        for (i in b.indices) {
            resultSb.append(byteToHexString(b[i]))
        }
        return resultSb.toString()
    }

    private fun byteToHexString(b: Byte): String {
        var n = b.toInt()
        if (n < 0) n += 256
        val d1 = n / 16
        val d2 = n % 16
        return hexDigits[d1] + hexDigits[d2]
    }

}