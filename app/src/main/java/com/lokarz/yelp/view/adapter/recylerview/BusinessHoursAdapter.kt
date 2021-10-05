package com.lokarz.yelp.view.adapter.recylerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lokarz.yelp.R
import com.lokarz.yelp.databinding.ItemBusinessHoursBinding
import com.lokarz.yelp.model.repository.poko.businessdetails.Open
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.DateUtil

class BusinessHoursAdapter(private val data: ArrayList<Open>) :
    RecyclerView.Adapter<BusinessHoursAdapter.ViewHolder>() {

    var onItemClickListener: AppListener.OnItemClickListener<Open>? = null

    class ViewHolder(val binding: ItemBusinessHoursBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBusinessHoursBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val open = data[position]
        val start = DateUtil.to12HourFormat(open.start)
        val end = DateUtil.to12HourFormat(open.end)

        with(holder.binding) {
            day = root.resources.getStringArray(R.array.day_of_week)[open.day]
            operatingHours = String.format("%s - %s", start, end)
            root.setOnClickListener {
                onItemClickListener?.onItemClick(open)
            }
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}