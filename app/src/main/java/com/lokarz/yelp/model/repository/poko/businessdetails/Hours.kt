package com.lokarz.yelp.model.repository.poko.businessdetails

import com.google.gson.annotations.SerializedName

data class Hours(

    @SerializedName("open") val open: ArrayList<Open>,
    @SerializedName("hours_type") val hours_type: String,
    @SerializedName("is_open_now") val is_open_now: Boolean
)