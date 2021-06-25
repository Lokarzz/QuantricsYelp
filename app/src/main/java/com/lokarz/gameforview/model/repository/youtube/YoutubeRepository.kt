package com.lokarz.gameforview.model.repository.youtube

import com.lokarz.gameforview.pojo.youtube.videoDetail.VideoDetailResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class YoutubeRepository @Inject constructor(private val youtubeRemoteRepository: YoutubeRemoteRepository) {

    fun getVideoDetail(url : String): Single<VideoDetailResponse> {
        return youtubeRemoteRepository.getVideoDetail(url)
    }
}