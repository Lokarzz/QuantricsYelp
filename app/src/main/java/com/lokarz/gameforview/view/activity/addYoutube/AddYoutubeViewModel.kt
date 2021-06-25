package com.lokarz.gameforview.view.activity.addYoutube

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.model.api.retrofit.youtube.IYoutubeService
import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.pojo.profile.ProfileData
import com.lokarz.gameforview.pojo.youtube.YoutubeData
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import com.lokarz.gameforview.pojo.youtube.videoDetail.VideoDetailResponse
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.PreferenceUtil
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

class AddYoutubeViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val preferenceUtil: PreferenceUtil,
    private val iYoutubeService: IYoutubeService
) : ViewModel() {

    val currentPoints: MutableLiveData<String> = MutableLiveData()

    init {
        processProfileData()
    }

    var googleAccount: GoogleAccount? = GsonUtil.getGson(
        preferenceUtil.readSavedData(GoogleAccount::class.simpleName),
        GoogleAccount::class.java
    )

    private fun getReviewCollection(): Single<YoutubeResponse> {
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
                    source.onSuccess(youtubeResponse)
                }
        }
    }

    private fun hasEnoughPoints(): Boolean {
        var profileData = GsonUtil.getGson(preferenceUtil, ProfileData::class.java)
        if (profileData == null) {
            profileData = ProfileData()
        }

        return profileData.gamingPoints!! >= 5
    }


    fun processProfileData() {
        var profileData = GsonUtil.getGson(preferenceUtil, ProfileData::class.java)
        if (profileData == null) {
            profileData = ProfileData()
        }
        currentPoints.value = "Game Points: ${profileData.gamingPoints}"
    }

    fun decreasePoints() {
        val profileData = GsonUtil.getGson(preferenceUtil, ProfileData::class.java)
        profileData ?: return
        profileData.gamingPoints?.minus(5)
        preferenceUtil.saveData(profileData::class.simpleName, GsonUtil.getGsonString(profileData))
        currentPoints.value = "Game Points: ${profileData.gamingPoints}"

    }


    fun addYoutubeVideoId(youtubeData: YoutubeData): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        if (hasEnoughPoints()) {
            youtubeData.googleAccount = googleAccount
            isValidId(youtubeData.videoId).subscribe { result ->
                if (result == true) {
                    getReviewCollection().subscribe { youtubeResponse ->
                        val data = youtubeResponse.data
                        if (data?.any { it.videoId == youtubeData.videoId } == false) {
                            data.add(youtubeData)
                            firebaseFirestore.collection(Constant.Firestore.VIDEOS_COLLECTION)
                                .document(Constant.Firestore.REVIEW_DOCUMENT)
                                .set(youtubeResponse)
                                .addOnCompleteListener {
                                    mutableLiveData.postValue(Constant.Success.VALID_ID)
                                    decreasePoints()
                                }
                                .addOnFailureListener {
                                    mutableLiveData.postValue(Constant.Error.SOMETHING_WENT_WRONG)
                                }
                        } else {
                            mutableLiveData.postValue(Constant.Error.ID_ALREADY_ADDED)
                        }
                    }
                } else {
                    mutableLiveData.postValue(Constant.Error.INVALID_ID)
                }
            }
        } else {
            mutableLiveData.postValue(Constant.Error.NOT_ENOUGH_POINTS)
        }
        return mutableLiveData
    }

    private fun isValidId(id: String): Single<Boolean> {
        return Single.create {

            val url = "http://www.youtube.com/watch?v=$id"
            iYoutubeService.getVideoDetail(url).enqueue(object : Callback<VideoDetailResponse> {
                override fun onResponse(
                    call: Call<VideoDetailResponse>,
                    response: Response<VideoDetailResponse>
                ) {
                    val body = response.body()
                    it.onSuccess(body != null)
                }

                override fun onFailure(call: Call<VideoDetailResponse>, t: Throwable) {
                    it.onSuccess(false)

                }


            })
        }
    }

    fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
    fun getVideoId(id: String): String {
        var ret = id
        if (id.isValidUrl()) {
            val uri = Uri.parse(id)
            when (uri.host) {
                Constant.Youtube.YOUTUBE_COM -> {
                    val videoId = uri.getQueryParameter("v")
                    if (!videoId.isNullOrEmpty()) {
                        ret = videoId
                    }
                }
                Constant.Youtube.YOUTU_BE -> {
                    val videoId = uri.lastPathSegment
                    if (!videoId.isNullOrEmpty()) {
                        ret = videoId
                    }
                }
            }

        }
        return ret
    }
}