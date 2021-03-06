package com.lokarz.yelp.model.repository.poko.businessdetails

import com.google.gson.annotations.SerializedName


data class Open (

	@SerializedName("is_overnight") val is_overnight : Boolean,
	@SerializedName("start") val start : String,
	@SerializedName("end") val end : String,
	@SerializedName("day") val day : Int
)