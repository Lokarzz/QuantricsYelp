package com.lokarz.gameforview.view.fragment.youtube

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.model.repository.firebase.FirebaseRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.pojo.youtube.YoutubeCard
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class YoutubeViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    var youtubeCard: MutableLiveData<YoutubeCard> = MutableLiveData()
    private var youtubeResponse: YoutubeResponse? = null
    private var pos: Int = 0
    var isPointsAdded: MutableLiveData<Boolean> = MutableLiveData()
    var currentPointsAdded = 0

    init {
        initVideos()
    }

    private fun initVideos() {
        firebaseRepository.getVideos().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                youtubeResponse = result
                initYoutubeCard()
            }

    }

    private fun initYoutubeCard() {
        youtubeCard.postValue(
            YoutubeCard(
                firstCard = youtubeResponse?.data?.get(pos),
                secondCard = youtubeResponse?.data?.get(pos + 1)
            )
        )
    }

    fun updateYoutubeCard() {
        currentPointsAdded = 0
        val dataSize = youtubeResponse?.data?.size!!
        pos++
        if (pos >= dataSize) {
            pos = 0
        }
        youtubeCard.postValue(
            YoutubeCard(
                firstCard = youtubeResponse?.data?.get(pos),
                secondCard = youtubeResponse?.data?.get(if (dataSize > pos + 1) pos + 1 else 0)
            )
        )
    }

    fun processPoints(second: Float) {
        val points: Int = (second / 60).toInt()
        if (points != currentPointsAdded) {
            currentPointsAdded = points
            profileRepository.addPoints(1).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ ->
                    isPointsAdded.postValue(true)
                }
        }
    }

}