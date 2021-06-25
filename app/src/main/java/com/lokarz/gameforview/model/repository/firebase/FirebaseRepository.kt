package com.lokarz.gameforview.model.repository.firebase

import com.lokarz.gameforview.pojo.youtube.YoutubeData
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import com.lokarz.gameforview.util.Constant
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseRemoteRepository: FirebaseRemoteRepository) {

    fun getVideos(): Single<YoutubeResponse> {
        return firebaseRemoteRepository.getVideos()
    }

    fun addVideo(youtubeData: YoutubeData): Single<Boolean> {
        return Single.create { source ->
            getVideos().subscribe { youtubeResponse ->
                val data = youtubeResponse.data
                if (data?.any { it.videoId == youtubeData.videoId } == false) {
                    data.add(youtubeData)
                    firebaseRemoteRepository.addVideo(youtubeResponse).subscribe({ _ ->
                        source.onSuccess(true)
                    }, { error ->
                        source.onError(Throwable(error))
                    })
                } else {
                    source.onError(Throwable(Constant.Error.ID_ALREADY_ADDED))
                }
            }
        }

    }
}