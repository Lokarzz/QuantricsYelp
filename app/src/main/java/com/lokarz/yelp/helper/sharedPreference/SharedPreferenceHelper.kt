package com.lokarz.yelp.helper.sharedPreference

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.lokarz.yelp.BuildConfig

class SharedPreferenceHelper(context: Context, masterKey: MasterKey) {

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    private val editor = sharedPreferences.edit()

    fun putString(key: String, value: String) {
        with(editor) {
            putString(key, value)
            apply()
        }
    }


    fun getString(key: String): String? {
        return sharedPreferences.getString(key, EMPTY)
    }

    companion object {
        private const val FILE_NAME = BuildConfig.APPLICATION_ID
        private const val EMPTY = ""
    }
}