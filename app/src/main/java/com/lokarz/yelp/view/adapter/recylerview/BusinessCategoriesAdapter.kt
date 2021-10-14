package com.lokarz.yelp.view.adapter.recylerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lokarz.yelp.databinding.ItemBusinessCategoriesBinding
import com.lokarz.yelp.model.repository.poko.Categories
import com.lokarz.yelp.util.AppListener

class BusinessCategoriesAdapter(private val data: ArrayList<Categories>) :
    RecyclerView.Adapter<BusinessCategoriesAdapter.ViewHolder>() {

    var onItemClickListener: AppListener.OnItemClickListener<Categories>? = null

    class ViewHolder(val binding: ItemBusinessCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusinessCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categories = data[position]

        with(holder.binding) {
            data = categories
            root.setOnClickListener {
                onItemClickListener?.onItemClick(categories)
            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}