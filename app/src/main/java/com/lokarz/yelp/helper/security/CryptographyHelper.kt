package com.lokarz.yelp.helper.security

import android.app.Application
import android.util.Base64
import androidx.security.crypto.MasterKey
import com.lokarz.yelp.BuildConfig
import com.lokarz.yelp.pojo.appKeys.AppKeysData
import com.lokarz.yelp.pojo.appKeys.AppKeysResponse
import com.lokarz.yelp.util.GsonUtil
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class CryptographyHelper(val context: Application) {

    private var keysResponse: AppKeysResponse? = null

    val masterKey : MasterKey by lazy {
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
    }

    init {
        initKeys()
    }

    fun generateKeys(): HashMap<String, String> {
        return hashMapOf(
            "secretKey" to generateSaltAndSecretKey(),
            "salt" to generateSaltAndSecretKey(),
            "iv" to generateIV()
        )
    }

    private fun generateIV(): String {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        val iv = ByteArray(cipher.blockSize)
        SecureRandom().nextBytes(iv)
        return Base64.encodeToString(iv, Base64.DEFAULT)
    }

    private fun generateSaltAndSecretKey(): String {
        val salt = ByteArray(16)
        val randomSecureRandom = SecureRandom()
        randomSecureRandom.nextBytes(salt)
        return Base64.encodeToString(salt, Base64.DEFAULT)
    }

    private fun initKeys() {
        val decryptedData = decrypt(APP_DATA)
        keysResponse = GsonUtil.getGson(decryptedData, AppKeysResponse::class.java)
    }

    fun getKeyData(): AppKeysData {
        return keysResponse?.data!!
    }

    fun encrypt(strToEncrypt: String): String {
        var ret = ""
        try {
            val ivParameterSpec = ivParameterSpec()

            val factory = factory()
            val spec = pbeKeySpec()
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp.encoded, AES)

            val cipher = cipher(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            ret = Base64.encodeToString(
                cipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)),
                Base64.DEFAULT
            )
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

    private fun decrypt(strToDecrypt: String): String {
        var ret = ""
        try {
            val ivParameterSpec = ivParameterSpec()

            val factory = factory()
            val spec = pbeKeySpec()
            val tmp = factory.generateSecret(spec)
            val secretKey = SecretKeySpec(tmp?.encoded, AES)

            val cipher = cipher(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec)
            ret = String(cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

    private fun pbeKeySpec(): PBEKeySpec {
        return PBEKeySpec(
            SECRET_KEY.toCharArray(),
            Base64.decode(SALT, Base64.DEFAULT),
            ITERATION_COUNT,
            KEY_LENGTH
        )
    }

    private fun ivParameterSpec(): IvParameterSpec {
        return IvParameterSpec(Base64.decode(IV, Base64.DEFAULT))
    }

    private fun factory(): SecretKeyFactory {
        return SecretKeyFactory.getInstance(SECRETE_KEY_FACTORY_ALGORITHM)
    }

    private fun cipher(
        mode: Int,
        secretKeySpec: SecretKeySpec,
        ivParameterSpec: IvParameterSpec
    ): Cipher {
        val cipher = Cipher.getInstance(CIPHER_TRANSFORMATION)
        cipher.init(mode, secretKeySpec, ivParameterSpec)
        return cipher
    }

    companion object {
        private const val SECRET_KEY = BuildConfig.KEY
        private const val SALT = BuildConfig.SALT
        private const val IV = BuildConfig.IV
        private const val APP_DATA = BuildConfig.APP_DATA
        private const val SECRETE_KEY_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1"
        private const val CIPHER_TRANSFORMATION = "AES/CBC/PKCS7Padding"
        private const val AES = "AES"
        private const val ITERATION_COUNT = 10000
        private const val KEY_LENGTH = 256
    }
}