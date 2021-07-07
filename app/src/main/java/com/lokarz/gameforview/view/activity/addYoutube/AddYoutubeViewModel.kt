package com.lokarz.gameforview.view.activity.addYoutube

import android.net.Uri
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.model.repository.firebase.FirebaseRepository
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.model.repository.youtube.YoutubeRepository
import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.pojo.profile.ProfileData
import com.lokarz.gameforview.pojo.youtube.YoutubeData
import com.lokarz.gameforview.util.Constant
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddYoutubeViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
    private val firebaseRepository: FirebaseRepository,
    private val profileRepository: ProfileRepository,
    googleRepository: GoogleRepository
) : ViewModel() {

    val currentPoints: MutableLiveData<String> = MutableLiveData()
    private var profileData: ProfileData? = null
    private var googleAccount: GoogleAccount? = null

    init {
        processProfileData()
        googleRepository.getData().subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { data ->
                googleAccount = data
            }
    }

    private infix fun <T : Comparable<T>> T?.isGreaterThanOrEqualTo(other: T?): Boolean =
        if (this != null && other != null) this >= other else false

    private fun hasEnoughPoints(): Boolean {
        return profileData?.gamingPoints isGreaterThanOrEqualTo 5
    }

    private fun processProfileData() {
        profileRepository.getProfile().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                profileData = data
                currentPoints.value = "Game Points: ${profileData?.gamingPoints}"
            }
    }

    private fun decreasePoints() {
        profileRepository.decreasePoints(5).subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe { _ ->
                refreshProfile()
            }
    }

    private fun refreshProfile() {
        processProfileData()
    }


    fun addYoutubeVideoId(youtubeData: YoutubeData): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()

        if (hasEnoughPoints()) {
            youtubeData.googleAccount = googleAccount
            isValidId(youtubeData.videoId).subscribe({ result ->
                if (result == true) {
                    firebaseRepository.addVideo(youtubeData).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            mutableLiveData.postValue(Constant.Success.VALID_ID)
                            decreasePoints()
                        }, { error ->
                            mutableLiveData.postValue(error.message)
                        })
                }
            }, { error ->
                mutableLiveData.postValue(error.message)
            })
        } else {
            mutableLiveData.postValue(Constant.Error.NOT_ENOUGH_POINTS)
        }
        return mutableLiveData
    }

    private fun isValidId(id: String): Single<Boolean> {
        return Single.create {

            val url = "http://www.youtube.com/watch?v=$id"
            youtubeRepository.getVideoDetail(url).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    it.onSuccess(response != null)
                }, { _ ->
                    it.onError(Throwable(Constant.Error.INVALID_ID))
                })
        }
    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

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