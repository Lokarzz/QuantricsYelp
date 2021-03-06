package com.lokarz.yelp.model.repository.poko.search

import com.google.gson.annotations.SerializedName



data class SearchResponse (

    @SerializedName("businesses") val businesses : List<Businesses>,
    @SerializedName("total") val total : Int,
    @SerializedName("region") val region : Region
)