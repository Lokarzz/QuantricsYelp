package com.lokarz.kotlinbaseapp.util

import android.content.Intent
import com.google.gson.Gson
import java.io.Reader

class GsonUtil {

    companion object{
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

        fun <T> getGson(reader: Reader?, clazz: Class<T>?): T? {
            val gson = Gson()
            var `object`: T? = null
            try {
                `object` = gson.fromJson(reader, clazz)
            } catch (e: Exception) {
                // do nothing
            }
            return `object`
        }
    }




}