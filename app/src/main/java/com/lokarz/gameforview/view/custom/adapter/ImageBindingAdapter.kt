package com.lokarz.gameforview.view.custom.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ImageBindingAdapter {

    companion object{
        @JvmStatic
        @BindingAdapter("srcImg")
        fun loadImage(appCompatImageView: AppCompatImageView?, srcImg: String?) {
            if (!srcImg.isNullOrEmpty() && appCompatImageView != null) {
                Glide.with(appCompatImageView.context)
                    .load(srcImg)
                    .into(appCompatImageView)
            }
        }

        @JvmStatic
        @BindingAdapter("srcImgFragment", "fragment")
        fun loadImageFragment(
            appCompatImageView: AppCompatImageView?,
            fragment: Fragment,
            srcImgFragment: String?
        ) {
            if (!srcImgFragment.isNullOrEmpty()&& appCompatImageView != null) {
                Glide.with(fragment)
                    .load(srcImgFragment)
                    .into(appCompatImageView)
            }
        }
    }


}