package com.lokarz.yelp.view.adapter.recylerview

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.lokarz.yelp.databinding.ItemRestaurantBinding
import com.lokarz.yelp.model.repository.poko.search.Businesses
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.Constant

class RestaurantAdapter(private val fragment: Fragment, private val data: ArrayList<Businesses>) :
    RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    var onBottomReachedListener: AppListener.OnBottomReachedListener? = null
    var onItemClickListener: AppListener.OnItemViewClickListener<Businesses, ItemRestaurantBinding>? =
        null

    class ViewHolder(val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val business = data[position]

        with(holder.binding) {
            data = business
            imageUrl = imageUrl(business)
            snippet = getSnippet(business)

            root.setOnClickListener {
                onItemClickListener?.onItemViewClick(business, this)
            }
        }
        triggerBottomReach(position)
    }

    private fun triggerBottomReach(position: Int) {
        if (onBottomReachedListener != null && data.size - 1 == position) {
            onBottomReachedListener?.onBottomReached()
            onBottomReachedListener = null
        }
    }

    private fun imageUrl(data: Businesses): String {
        var imageUrl = data.imageUrl
        if (imageUrl.isEmpty()) {
            imageUrl = Constant.Image.ITEM_DEFAULT_IMAGE
        }
        return imageUrl
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