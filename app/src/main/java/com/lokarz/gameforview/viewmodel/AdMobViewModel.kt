package com.lokarz.gameforview.viewmodel

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdMobViewModel : ViewModel() {

    var mRewardedAd: RewardedAd? = null

    fun getAdRequest(): AdRequest? {
        return AdRequest.Builder().build()
    }

    fun getAdManagerAdRequest(): AdManagerAdRequest {
        return AdManagerAdRequest.Builder().build()
    }

    fun initReward(context: Context) {
        MobileAds.initialize(context) {}
        RewardedAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917",
            getAdManagerAdRequest(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TAG", adError?.message)
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    Log.d("TAG", "Ad was loaded.")
                    mRewardedAd = rewardedAd
                    initFullScreenCallBack()

                }
            })
    }

    fun initFullScreenCallBack() {
        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("TAG", "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d("TAG", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("TAG", "Ad showed fullscreen content.")
                mRewardedAd = null
            }
        }
    }

    fun showReward(activity: AppCompatActivity, onUserEarnedRewardListener: OnUserEarnedRewardListener) {
        mRewardedAd?.show(activity, onUserEarnedRewardListener)
    }
}