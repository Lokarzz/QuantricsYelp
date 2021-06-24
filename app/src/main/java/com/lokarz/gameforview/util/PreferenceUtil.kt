package com.lokarz.gameforview.util

import android.content.Context

class PreferenceUtil(val context: Context) {

    fun saveData(key: String?, value: String?, filename: String? = PREFERENCE_NAME) {
        val editor = context.getSharedPreferences(
            filename,
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun readSavedData(key: String?, filename: String? = PREFERENCE_NAME): String? {
        val sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
        return sharedPreferences.getString(
            key,
            ""
        )
    }

    fun clearSavedData(filename: String? = PREFERENCE_NAME) {
        val editor =
            context.getSharedPreferences(
                filename,
                Context.MODE_PRIVATE
            ).edit()
        editor.clear()
        editor.apply()
    }


    companion object {
        private const val PREFERENCE_NAME = "com.lokarz.gameforview"


    }
}