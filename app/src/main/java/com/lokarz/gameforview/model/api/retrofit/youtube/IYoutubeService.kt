package com.lokarz.gameforview.model.api.retrofit.youtube

import com.lokarz.gameforview.pojo.youtube.videoDetail.VideoDetailResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IYoutubeService {



    @GET("/oembed?format=json")
    fun getVideoDetail(@Query("url") url: String): Single<VideoDetailResponse>

}