package com.lokarz.yelp.view.adapter.binding

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

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

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "defaultImage"], requireAll = false)
    fun loadImage(view: AppCompatImageView, url: String?, defaultImage : String?) {
        Glide.with(view.context).load(url ?: defaultImage ?: "").into(view)
    }
}