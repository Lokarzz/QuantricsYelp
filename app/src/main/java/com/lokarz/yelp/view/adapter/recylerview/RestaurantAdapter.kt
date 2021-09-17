package com.lokarz.yelp.view.adapter.recylerview

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lokarz.yelp.R
import com.lokarz.yelp.pojo.yelp.search.Businesses
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.Constant

class RestaurantAdapter(private val fragment: Fragment, private val data: ArrayList<Businesses>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    var onBottomReachedListener: AppListener.OnBottomReachedListener? = null
    var onItemClickListener: AppListener.OnItemClickListener<Businesses>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: AppCompatImageView = view.findViewById(R.id.iv_image)
        val tvName: AppCompatTextView = view.findViewById(R.id.tv_name)
        val tvDescription: AppCompatTextView = view.findViewById(R.id.tv_description)
        val ratingBar: AppCompatRatingBar = view.findViewById(R.id.rating_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = data[position]

        var imageUrl = business.imageUrl
        if (imageUrl.isEmpty()) {
            imageUrl = Constant.Image.ITEM_DEFAULT_IMAGE
        }
        Glide.with(fragment).load(imageUrl)
            .into(holder.ivImage)

        holder.tvName.text = business.name
        holder.tvDescription.text = getSnippet(business)
        holder.ratingBar.rating = business.rating

        if (onBottomReachedListener != null && data.size - 1 == position) {
            onBottomReachedListener?.onBottomReached()
            onBottomReachedListener = null
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(business)
        }
    }

    private fun getSnippet(data: Businesses): String {
        val stringArray = ArrayList<String>()

        data.price?.let {
            stringArray.add(it)
        }
        data.displayPhone?.let {
            stringArray.add(it)
        }

        return TextUtils.join(" - ", stringArray)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun onNewData(data: List<Businesses>) {
        val currentSize = this.data.size
        this.data.addAll(data)
        notifyItemRangeInserted(currentSize, data.size)
    }

    fun clearData() {
        onBottomReachedListener = null
        val currentSize = this.data.size
        data.clear()
        notifyItemRangeRemoved(0, currentSize)
    }

}