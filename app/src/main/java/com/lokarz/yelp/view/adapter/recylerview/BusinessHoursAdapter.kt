package com.lokarz.yelp.view.adapter.recylerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.lokarz.yelp.R
import com.lokarz.yelp.pojo.yelp.businessdetails.Open
import com.lokarz.yelp.util.AppListener
import com.lokarz.yelp.util.DateUtil

class BusinessHoursAdapter(private val data: ArrayList<Open>) :
    RecyclerView.Adapter<BusinessHoursAdapter.ViewHolder>() {

    var onItemClickListener: AppListener.OnItemClickListener<Open>? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDay: AppCompatTextView = view.findViewById(R.id.tv_day)
        val tvOperatingHours: AppCompatTextView = view.findViewById(R.id.tv_operation_hours)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_business_hours, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val open = data[position]
        val context = holder.itemView.context
        val start = DateUtil.to12HourFormat(open.start)
        val end = DateUtil.to12HourFormat(open.end)

        holder.tvDay.text = context.resources.getStringArray(R.array.day_of_week)[open.day]

        holder.tvOperatingHours.text = String.format("%s - %s", start, end)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(open)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

}