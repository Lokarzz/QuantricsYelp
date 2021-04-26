package com.lokarz.kotlinbaseapp.util

import android.content.Context

class PreferenceUtil {


    companion object {
        private const val FILE_NAME = "Preferences"




        fun saveData( context: Context, key: String?, value: String?,filename: String? = FILE_NAME) {
            val editor = context.getSharedPreferences(
                filename,
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun readSavedData( context: Context, key: String?, filename: String? = FILE_NAME): String? {
            val sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
            return sharedPreferences.getString(
                key,
                ""
            )
        }

        fun clearSavedData(context: Context, filename: String? = FILE_NAME) {
            val editor =
                context.getSharedPreferences(filename,
                    Context.MODE_PRIVATE).edit()
            editor.clear()
            editor.apply()
        }
    }
}