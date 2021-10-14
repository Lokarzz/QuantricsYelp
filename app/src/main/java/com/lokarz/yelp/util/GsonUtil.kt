package com.lokarz.yelp.util

import com.google.gson.Gson

object GsonUtil {

    private const val EMPTY = ""

    fun <T> getGson(json: String?, clazz: Class<T>?): T? {
        val gson = Gson()
        var ret: T? = null
        try {
            ret = gson.fromJson(json, clazz)
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

    fun toString(`object`: Any?): String {
        var ret = EMPTY
        try {
            ret = Gson().toJson(`object`)
        } catch (e: Exception) {
            // do nothing
        }
        return ret
    }

}