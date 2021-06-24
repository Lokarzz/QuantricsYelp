package com.lokarz.gameforview.api.retrofit.google

import com.lokarz.gameforview.pojo.youtube.videoDetail.VideoDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IGoogleService {



    @GET("/oembed?format=json")
    fun getVideoDetail(@Query("url") id: String): Call<VideoDetailResponse>

}