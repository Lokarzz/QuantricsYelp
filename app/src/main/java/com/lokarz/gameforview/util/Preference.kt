package com.lokarz.gameforview.util

import android.content.Context
import io.reactivex.rxjava3.core.Single
import java.lang.Exception

class Preference(val context: Context) {

    fun saveDataRx(
        key: String?,
        value: String?,
        filename: String? = PREFERENCE_NAME
    ): Single<Boolean> {
        return Single.create {

            try {
                val editor = context.getSharedPreferences(
                    filename,
                    Context.MODE_PRIVATE
                ).edit()
                editor.putString(key, value)
                editor.apply()

                it.onSuccess(true)
            } catch (e: Exception) {
                it.onError(Throwable(e.message))
            }

        }
    }

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

    fun readSavedDataRx(key: String?, filename: String? = PREFERENCE_NAME): Single<String>? {
        return Single.create {
            val sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE)
            it.onSuccess(
                sharedPreferences.getString(
                    key,
                    ""
                )
            )
        }

    }

    fun clearData(key: String?, filename: String? = PREFERENCE_NAME): Single<Boolean> {
        return Single.create {
            val editor =
                context.getSharedPreferences(
                    filename,
                    Context.MODE_PRIVATE
                ).edit()
            if (key != null) {
                editor.remove(key)
            } else {
                editor.clear()
            }
            editor.apply()
            it.onSuccess(true)
        }

    }

    companion object {
        private const val PREFERENCE_NAME = "com.lokarz.gameforview"

    }
}