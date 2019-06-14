package cn.woochen.common_config.util

import android.text.TextUtils
import java.io.InputStream
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.PublicKey


/**
 * 加密解密工具类
 * @author woochen
 * @time 2017/8/24 10:21
 * @desc
 */

object EncryptUtil {
    var hexDigits = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f")

    fun encryptMD5(originString: String?): String {
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

    //    ------------------<rsa start>--------------------


    /**
     * 加载私钥
     */
    fun loadPrivateKey(inPrivate: InputStream) = RsaUtil.loadPrivateKey(inPrivate)

    /**
     * 加载私钥
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     */
    fun loadPrivateKey(privateKeyStr: String) = RsaUtil.loadPrivateKey(privateKeyStr)


    /**
     * 加载公钥
     */
    fun loadPublicKey(inPub: InputStream) = RsaUtil.loadPublicKey(inPub)

    /**
     * 加载公钥
     */
    fun loadPublicKey(publicKeyStr: String) = RsaUtil.loadPublicKey(publicKeyStr)

    /**
     *私钥解密
     */
    fun decryptVideoByRsa(source: String?, privateKey: PrivateKey): String {
        if (TextUtils.isEmpty(source)) return ""
        val decryptByte = RsaUtil.decryptData(Base64Utils.decode(source), privateKey)
        return String(decryptByte)
    }

    /**
     *公钥加密
     */
    fun encryptDocByRsa(source: String?, publicKey: PublicKey): String {
        if (TextUtils.isEmpty(source)) return ""
        val encryptByte = RsaUtil.encryptData(source?.toByteArray(), publicKey)
        // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
        return Base64Utils.encode(encryptByte)
    }


//    ------------------<rsa end>--------------------

}