package com.lokarz.gameforview.model.repository.youtube

import com.lokarz.gameforview.model.api.retrofit.youtube.IYoutubeService
import com.lokarz.gameforview.pojo.youtube.videoDetail.VideoDetailResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class YoutubeRemoteRepository @Inject constructor(private val iYoutubeService: IYoutubeService) {


    fun getVideoDetail(url: String): Single<VideoDetailResponse> {
        return iYoutubeService.getVideoDetail(url)
    }
}