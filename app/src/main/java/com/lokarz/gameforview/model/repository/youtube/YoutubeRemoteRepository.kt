package com.lokarz.gameforview.model.repository.youtube

import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.model.api.retrofit.youtube.IYoutubeService
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import com.lokarz.gameforview.pojo.youtube.videoDetail.VideoDetailResponse
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.util.GsonUtil
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class YoutubeRemoteRepository @Inject constructor(private val iYoutubeService: IYoutubeService) {


    fun getVideoDetail(url: String): Single<VideoDetailResponse> {
        return iYoutubeService.getVideoDetail(url)
    }
}