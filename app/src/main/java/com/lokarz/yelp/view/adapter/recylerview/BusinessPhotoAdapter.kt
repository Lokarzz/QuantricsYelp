package com.lokarz.yelp.view.adapter.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lokarz.yelp.R
import com.lokarz.yelp.util.AppListener

class BusinessPhotoAdapter(private val activity: AppCompatActivity, private val data: ArrayList<String>) :
    RecyclerView.Adapter<BusinessPhotoAdapter.ViewHolder>() {

    var onItemClickListener: AppListener.OnItemClickListener<String>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPhoto: AppCompatImageView = view.findViewById(R.id.iv_photo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_business_photo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = data[position]

        Glide.with(activity).load(photo)
            .into(holder.ivPhoto)


        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(photo)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun onNewData(data: List<String>) {
        val currentSize = this.data.size
        this.data.addAll(data)
        notifyItemRangeInserted(currentSize, data.size)
    }
}