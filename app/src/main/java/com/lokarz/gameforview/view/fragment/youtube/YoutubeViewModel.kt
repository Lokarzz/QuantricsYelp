package com.lokarz.gameforview.view.fragment.youtube

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.pojo.profile.ProfileData
import com.lokarz.gameforview.pojo.youtube.YoutubeCard
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.PreferenceUtil
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class YoutubeViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    var preferenceUtil: PreferenceUtil,
) : ViewModel() {

    var youtubeCard: MutableLiveData<YoutubeCard> = MutableLiveData()
    private var youtubeResponse: YoutubeResponse? = null
    private var pos: Int = 0
    var isPointsAdded: MutableLiveData<Boolean> = MutableLiveData()
    var currentPointsAddesd = 0

    init {
        isPointsAdded.value = false
        getReviewCollection().subscribe { result ->
            youtubeResponse = result
            initYoutubeCard()
        }
    }

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
                    youtubeResponse.data?.shuffle()
                    source.onSuccess(youtubeResponse)
                }
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
        currentPointsAddesd = 0
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
        if (points != currentPointsAddesd) {
            currentPointsAddesd = points
            var profileData = GsonUtil.getGson(preferenceUtil, ProfileData::class.java)
            if (profileData == null) {
                profileData = ProfileData()
            }
            profileData.gamingPoints++
            preferenceUtil.saveData(
                ProfileData::class.simpleName,
                GsonUtil.getGsonString(profileData)
            )
            isPointsAdded.postValue(true)
        }
    }

}