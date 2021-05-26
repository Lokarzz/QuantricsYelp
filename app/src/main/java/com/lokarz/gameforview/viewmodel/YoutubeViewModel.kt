package com.lokarz.gameforview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokarz.gameforview.pojo.youtube.YoutubeCard
import com.lokarz.gameforview.pojo.youtube.YoutubeData
import com.lokarz.gameforview.pojo.youtube.YoutubeResponse
import javax.inject.Inject

class YoutubeViewModel @Inject constructor() : ViewModel() {

    var youtubeCard: MutableLiveData<YoutubeCard> = MutableLiveData();
    private val youtubeResponse = YoutubeResponse()
    private var pos: Int = 0

    fun init() {
        getYouTubeData()
        initYoutubeCard()
    }

    private fun getYouTubeData() {
        val youtubeData: ArrayList<YoutubeData> = ArrayList()
        youtubeData.add(YoutubeData("8hGGVW_mGEI"))
        youtubeData.add(YoutubeData("cOrYjwAJnrk"))
        youtubeData.add(YoutubeData("jxvwn62P_T4"))
        youtubeData.add(YoutubeData("Kro9FidqVbs"))
        youtubeData.add(YoutubeData("Doth_fHRS6o"))
        youtubeResponse.data = youtubeData
    }

    private fun initYoutubeCard() {
        youtubeCard.postValue(
            YoutubeCard(
                firstCard = youtubeResponse.data?.get(pos),
                secondCard = youtubeResponse.data?.get(pos + 1)
            )
        )
    }

    fun updateYoutubeCard() {
        val dataSize = youtubeResponse.data?.size!!
        pos++
        if (pos >= dataSize) {
            pos = 0
        }
        youtubeCard.postValue(
            YoutubeCard(
                firstCard = youtubeResponse.data?.get(pos),
                secondCard = youtubeResponse.data?.get(if (dataSize > pos + 1) pos + 1 else 0)
            )
        )
    }

}