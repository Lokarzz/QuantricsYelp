package com.lokarz.gameforview.util

import android.view.View

object ViewUtil {

    fun showView(show: Boolean, vararg views: View?) {
        for (view in views) {
            view?.visibility = if (show) View.VISIBLE else View.GONE
        }
    }
}