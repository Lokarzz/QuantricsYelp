package com.lokarz.gameforview.util

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class EncryptionUtil {

    fun generateKeys(): HashMap<String, String> {
        return hashMapOf(
            "secretKey" to generateSaltAndSecretKey(),
            "salt" to generateSaltAndSecretKey(),
            "iv" to generateIV()
        )
    }

    private fun generateIV(): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        val randomSecureRandom = SecureRandom()
        val iv = ByteArray(cipher.blockSize)
        randomSecureRandom.nextBytes(iv)
        return Base64.encodeToString(iv, Base64.DEFAULT)
    }

    private fun generateSaltAndSecretKey(): String {
        val salt = ByteArray(16)
        val randomSecureRandom = SecureRandom()
        randomSecureRandom.nextBytes(salt)
        return Base64.encodeToString(salt, Base64.DEFAULT)
    }

    fun encrypt(strToEncrypt: String): String {
        var ret = ""
        try {
            val ivParameterSpec = IvParameterSpec(Base64.decode(IV, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance(SECRETE_KEY_FACTORY_ALGORITHM)
            val spec = PBEKeySpec(
                SECRET_KEY.toCharArray(),
                Base64.decode(SALT, Base64.DEFAULT),
                ITERATION_COUNT,
                KEY_LENGTH
            )
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, AES)

            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            ret = Base64.encodeToString(
                cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)),
                Base64.DEFAULT
            )
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

    fun decrypt(strToDecrypt: String): String {
        var ret = ""
        try {
            val ivParameterSpec = IvParameterSpec(Base64.decode(IV, Base64.DEFAULT))

            val factory = SecretKeyFactory.getInstance(SECRETE_KEY_FACTORY_ALGORITHM)
            val spec = PBEKeySpec(
                SECRET_KEY.toCharArray(),
                Base64.decode(SALT, Base64.DEFAULT),
                ITERATION_COUNT,
                KEY_LENGTH
            )
            val tmp = factory.generateSecret(spec);
            val secretKey = SecretKeySpec(tmp.encoded, AES)

            val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            ret = String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

    companion object {
        private const val SECRET_KEY = "BuildConfig.KEY"
        private const val SALT = "BuildConfig.SALT"
        private const val IV = "BuildConfig.IV"
        private const val SECRETE_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
        private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS7Padding";
        private const val AES = "AES";
        private const val ITERATION_COUNT = 10000;
        private const val KEY_LENGTH = 256;
    }
}