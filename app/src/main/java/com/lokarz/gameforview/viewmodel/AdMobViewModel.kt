package com.lokarz.gameforview.viewmodel

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.lokarz.gameforview.util.Constant

class AdMobViewModel(context: Context) : ViewModel() {

    var mRewardedAd: RewardedAd? = null
    val rewardItem: MutableLiveData<String> = MutableLiveData()

    init {
        MobileAds.initialize(context) {}
        RewardedAd.load(
            context,
            "ca-app-pub-5188779324240142/9478397327",
            getAdManagerAdRequest(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("AdMobViewModel", adError.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d("AdMobViewModel", "Ad was loaded.")
                    mRewardedAd = rewardedAd
                    initFullScreenCallBack()

                }
            })
    }

    fun getAdRequest(): AdRequest? {
        return AdRequest.Builder().build()
    }

    fun getAdManagerAdRequest(): AdManagerAdRequest {
        return AdManagerAdRequest.Builder().build()
    }

    fun initFullScreenCallBack() {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                rewardItem.postValue(Constant.Error.REWARD_ALREADY_SHOWN)
            }

            override fun onAdShowedFullScreenContent() {
            }

            override fun onAdImpression() {
            }
        }
    }

    fun showReward(activity: AppCompatActivity) {
        mRewardedAd?.show(activity, onEarnReward())
    }

    private fun onEarnReward(): OnUserEarnedRewardListener {
        return OnUserEarnedRewardListener {
            rewardItem.postValue(Constant.Success.REWARD_SUCCESS)
        }
    }
}