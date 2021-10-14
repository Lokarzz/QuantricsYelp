package com.lokarz.yelp.util

object AppListener {

    interface OnBottomReachedListener {
        fun onBottomReached()
    }

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    interface OnItemViewClickListener<T, V> {
        fun onItemViewClick(item: T, view : V)
    }

    interface OnApplyFilterListener {
        fun onApplyFilter(map: HashMap<String, String>?)
    }
}