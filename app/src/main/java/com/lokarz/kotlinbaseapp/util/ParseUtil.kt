package com.lokarz.kotlinbaseapp.util

class ParseUtil {
    companion object {
        fun getInt(value: String): Int {
            var ret = 0;
            try {
                ret = Integer.parseInt(value)
            } catch (e : Exception) {
                // do nothing
            }

            return ret;
        }
        fun getInt(value: Float): Int {
            var ret = 0;
            try {
                ret = value.toInt()
            } catch (e : Exception) {
                // do nothing
            }

            return ret;
        }
    }
}