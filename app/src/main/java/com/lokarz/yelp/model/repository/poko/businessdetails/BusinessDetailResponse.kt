package com.lokarz.yelp.model.repository.poko.businessdetails

import com.google.gson.annotations.SerializedName
import com.lokarz.yelp.model.repository.poko.Categories
import com.lokarz.yelp.model.repository.poko.Location
import com.lokarz.yelp.model.repository.poko.search.Coordinates

data class BusinessDetailResponse(
    @SerializedName("id") val id: String,
    @SerializedName("alias") val alias: String,
    @SerializedName("name") val name: String,
    @SerializedName("is_claimed") val isClaimed: Boolean,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    @SerializedName("url") val url: String,
    @SerializedName("review_count") val reviewCount: Int,
    @SerializedName("categories") val categories: ArrayList<Categories>?,
    @SerializedName("rating") val rating: Float,
    @SerializedName("coordinates") val coordinates: Coordinates,
    @SerializedName("transactions") val transactions: List<String>,
    @SerializedName("price") val price: String,
    @SerializedName("location") val location: Location,
    @SerializedName("phone") val phone: String,
    @SerializedName("display_phone") val displayPhone: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("photos") val photos: ArrayList<String>,
    @SerializedName("hours") val hours: List<Hours>

)
