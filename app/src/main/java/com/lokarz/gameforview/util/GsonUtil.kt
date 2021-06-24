package com.lokarz.gameforview.util

import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lokarz.gameforview.pojo.google.GoogleAccount
import java.io.Reader

class GsonUtil {

    companion object {
        fun <T> getGson(json: String?, clazz: Class<T>?): T? {
            val gson = Gson()
            var `object`: T? = null
            try {
                `object` = gson.fromJson(json, clazz)
            } catch (e: Exception) {
                // do nothing
            }
            return `object`
        }

        fun <T> getGson(preferenceUtil: PreferenceUtil, clazz: Class<T>?): T? {
            val gson = Gson()
            var `object`: T? = null
            try {
                `object` = gson.fromJson(preferenceUtil.readSavedData(clazz?.simpleName), clazz)
            } catch (e: Exception) {
                // do nothing
            }
            return `object`
        }


        fun <T> getGson(intent: Intent, clazz: Class<T>): T? {
            val gson = Gson()
            var `object`: T? = null
            try {
                `object` = gson.fromJson(intent.getStringExtra(clazz.simpleName), clazz)
            } catch (e: Exception) {
                // do nothing
            }
            return `object`
        }

        fun getGsonString(`object`: Any?): String? {
            var ret: String? = ""
            try {
                ret = Gson().toJson(`object`)
            } catch (e: Exception) {
                if (`object` !is String) e.stackTrace
            }
            return ret
        }

        fun <T> getGson(
            map:
          Map<String?, Any?>?, clazz: Class<T>?
        ): T? {
            val gson = Gson()
            var `object`: T? = null
            try {
                `object` = getGson(gson.toJson(map).toString(), clazz)
            } catch (e: Exception) {
                // do nothing
            }
            return `object`
        }

    }


}