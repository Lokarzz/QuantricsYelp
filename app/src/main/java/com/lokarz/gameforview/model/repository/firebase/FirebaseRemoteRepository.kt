package com.lokarz.gameforview.model.repository.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.util.GsonUtil
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FirebaseRemoteRepository @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    fun getVideos(): Single<YoutubeResponse> {
        return Single.create { source ->
            firebaseFirestore.collection(Constant.Firestore.VIDEOS_COLLECTION)
                .document(Constant.Firestore.REVIEW_DOCUMENT)
                .get()
                .addOnSuccessListener {
                    var youtubeResponse = GsonUtil.getGson(it.data, YoutubeResponse::class.java)
                    if (youtubeResponse == null) {
                        youtubeResponse = YoutubeResponse()
                        youtubeResponse.data = ArrayList()
                    }
                    youtubeResponse.data?.shuffle()
                    source.onSuccess(youtubeResponse)
                }
        }
    }

    fun addVideo(youtubeResponse: YoutubeResponse): Single<Boolean> {
        return Single.create { source ->
            firebaseFirestore.collection(Constant.Firestore.VIDEOS_COLLECTION)
                .document(Constant.Firestore.REVIEW_DOCUMENT)
                .set(youtubeResponse)
                .addOnCompleteListener {
                    source.onSuccess(true)
                }
                .addOnFailureListener {
                    source.onError(Throwable(Constant.Error.SOMETHING_WENT_WRONG))
                }
        }
    }
}