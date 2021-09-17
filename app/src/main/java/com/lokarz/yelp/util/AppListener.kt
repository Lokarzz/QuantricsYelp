package com.lokarz.yelp.util

class AppListener {

    interface OnBottomReachedListener {
        fun onBottomReached()
    }

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    interface OnApplyFilterListener {
        fun onApplyFilter(map: HashMap<String, String>?)
    }
}