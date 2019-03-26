package cn.woochen.common_config.util


import android.util.Base64
import java.security.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import kotlin.experimental.and

/**
 * 加密解密工具类
 * @author woochen
 * @time 2017/8/24 10:21
 * @desc 功能简述: 加密解密工具类，对MD5/BASE64/DES/RSA等算法提供了包装.
 */

object EncryptUtil {

    private val KEY_SIZE = 1024
    private val MD5_ALGORITHM = "md5"
    private val DES_ALGORITHM = "des"
    private val RSA_ALGORITHM = "rsa"
    private val SIGNATURE_ALGORITHM = "MD5withRSA"

    private var md5: MessageDigest? = null
    private var random: SecureRandom? = null
    private var keyPair: KeyPair? = null

    init {
        try {
            md5 = MessageDigest.getInstance(MD5_ALGORITHM)
            val keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM)
            keyPairGenerator.initialize(KEY_SIZE)
            keyPair = keyPairGenerator.generateKeyPair()
        } catch (e: NoSuchAlgorithmException) {
        }
        random = SecureRandom()
    }

    /**
     * 功能简述: 使用md5进行单向加密.
     */
    fun encryptMD5(plainText: String): String {
        val cipherData = md5!!.digest(plainText.toByteArray())
        val builder = StringBuilder()
        for (cipher in cipherData) {
            val toHexStr = Integer.toHexString((cipher and 0xff.toByte()).toInt())
            builder.append(if (toHexStr.length == 1) "0$toHexStr" else toHexStr)
        }
        return builder.toString()
    }

    /**
     * 功能简述: 使用BASE64进行加密.
     * @param plainData 明文数据
     * @return 加密之后的文本内容
     */
    fun encryptBASE64(plainData: ByteArray): String {
        return Base64.encodeToString(plainData, Base64.DEFAULT)
    }

    /**
     * 功能简述: 使用BASE64进行解密.
     * @param cipherText 密文文本
     * @return 解密之后的数据
     */
    fun decryptBASE64(cipherText: String): ByteArray? {
        var plainData: ByteArray? = null
        try {
            plainData = Base64.decode(cipherText, Base64.DEFAULT)
        } catch (e: Exception) {
        }

        return plainData
    }

    /**
     * 功能简述: 使用DES算法进行加密.
     * @param plainData 明文数据
     * @param key   加密密钥
     * @return
     */
    fun encryptDES(plainData: ByteArray, key: String): ByteArray? {
        return processCipher(plainData, createSecretKey(key), Cipher.ENCRYPT_MODE, DES_ALGORITHM)
    }

    /**
     * 功能简述: 使用DES算法进行解密.
     * @param cipherData    密文数据
     * @param key   解密密钥
     * @return
     */
    fun decryptDES(cipherData: ByteArray, key: String): ByteArray? {
        return processCipher(cipherData, createSecretKey(key), Cipher.DECRYPT_MODE, DES_ALGORITHM)
    }

    /**
     * 功能简述: 根据key创建密钥SecretKey.
     * @param key
     * @return
     */
    private fun createSecretKey(key: String): SecretKey? {
        var secretKey: SecretKey? = null
        try {
            val keySpec = DESKeySpec(key.toByteArray())
            val keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM)
            secretKey = keyFactory.generateSecret(keySpec)
        } catch (e: Exception) {
        }

        return secretKey
    }

    /**
     * 功能简述: 加密/解密处理流程.
     * @param processData   待处理的数据
     * @param key   提供的密钥
     * @param opsMode   工作模式
     * @param algorithm   使用的算法
     * @return
     */
    private fun processCipher(processData: ByteArray, key: Key?, opsMode: Int, algorithm: String): ByteArray? {
        try {
            val cipher = Cipher.getInstance(algorithm)
            cipher.init(opsMode, key, random)
            return cipher.doFinal(processData)
        } catch (e: Exception) {
        }

        return null
    }

    /**
     * 功能简述: 创建私钥，用于RSA非对称加密.
     * @return
     */
    fun createPrivateKey(): PrivateKey {
        return keyPair!!.private
    }

    /**
     * 功能简述: 创建公钥，用于RSA非对称加密.
     * @return
     */
    fun createPublicKey(): PublicKey {
        return keyPair!!.public
    }

    /**
     * 功能简述: 使用RSA算法加密.
     * @param plainData 明文数据
     * @param key   密钥
     * @return
     */
    fun encryptRSA(plainData: ByteArray, key: Key): ByteArray? {
        return processCipher(plainData, key, Cipher.ENCRYPT_MODE, RSA_ALGORITHM)
    }

    /**
     * 功能简述: 使用RSA算法解密.
     * @param cipherData    密文数据
     * @param key   密钥
     * @return
     */
    fun decryptRSA(cipherData: ByteArray, key: Key): ByteArray? {
        return processCipher(cipherData, key, Cipher.DECRYPT_MODE, RSA_ALGORITHM)
    }

    /**
     * 功能简述: 使用私钥对加密数据创建数字签名.
     * @param cipherData     已经加密过的数据
     * @param privateKey    私钥
     * @return
     */
    fun createSignature(cipherData: ByteArray, privateKey: PrivateKey): ByteArray? {
        try {
            val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
            signature.initSign(privateKey)
            signature.update(cipherData)
            return signature.sign()
        } catch (e: Exception) {
        }

        return null
    }

    /**
     * 功能简述: 使用公钥对数字签名进行验证.
     * @param signData  数字签名
     * @param publicKey 公钥
     * @return
     */
    fun verifySignature(cipherData: ByteArray, signData: ByteArray, publicKey: PublicKey): Boolean {
        try {
            val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
            signature.initVerify(publicKey)
            signature.update(cipherData)
            return signature.verify(signData)
        } catch (e: Exception) {
        }

        return false
    }
}