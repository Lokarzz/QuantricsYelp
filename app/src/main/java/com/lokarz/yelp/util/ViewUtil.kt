package com.lokarz.yelp.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity


object ViewUtil {

    fun hideKeyboard(activity: AppCompatActivity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity.currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun setClickListener(listener: View.OnClickListener, vararg views: View) {
        for (view in views) {
            view.setOnClickListener(listener)
        }
    }
}