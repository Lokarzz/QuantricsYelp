package com.lokarz.yelp.view.adapter.binding

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {

    @BindingAdapter("visibility")
    @JvmStatic
    fun setVisibility(view: View, value: Boolean) {
        val isViewVisible = view.visibility == View.VISIBLE
        if (value == isViewVisible) {
            return
        }

        view.visibility = if (value) View.VISIBLE else View.GONE
    }
}