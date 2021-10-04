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
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemBusinessCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chip = data[position]
        holder.binding.chip.text = chip.title
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(chip)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}