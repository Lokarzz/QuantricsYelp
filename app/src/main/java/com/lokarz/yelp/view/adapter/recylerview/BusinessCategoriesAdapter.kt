package com.lokarz.yelp.view.adapter.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.lokarz.yelp.R
import com.lokarz.yelp.pojo.yelp.Categories
import com.lokarz.yelp.util.AppListener

class BusinessCategoriesAdapter(private val data: ArrayList<Categories>) :
    RecyclerView.Adapter<BusinessCategoriesAdapter.ViewHolder>() {

    var onItemClickListener: AppListener.OnItemClickListener<Categories>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chip: Chip = view.findViewById(R.id.chip)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_business_categories, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chip = data[position]

        holder.chip.text = chip.title

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(chip)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}