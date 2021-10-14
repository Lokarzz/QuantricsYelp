package com.lokarz.yelp.view.adapter.recylerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lokarz.yelp.databinding.ItemBusinessPhotoBinding
import com.lokarz.yelp.util.AppListener

class BusinessPhotoAdapter(
    private val activity: AppCompatActivity,
    private val data: ArrayList<String>
) :
    RecyclerView.Adapter<BusinessPhotoAdapter.ViewHolder>() {

    var onItemClickListener: AppListener.OnItemClickListener<String>? = null

    class ViewHolder(val binding: ItemBusinessPhotoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusinessPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = data[position]

        with(holder.binding) {
            imageUrl = photo

            root.setOnClickListener {
                onItemClickListener?.onItemClick(photo)
            }
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