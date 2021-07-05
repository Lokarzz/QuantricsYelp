package com.lokarz.gameforview.model.util

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.lokarz.gameforview.util.Constant
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class AdMob(private val appCompatActivity: AppCompatActivity) {

    var mRewardedAd: RewardedAd? = null
    var singleEmitter: SingleEmitter<String>? = null

    init {
        MobileAds.initialize(appCompatActivity) {}
        RewardedAd.load(
            appCompatActivity,
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
                singleEmitter?.onError(Throwable(Constant.Error.REWARD_ALREADY_SHOWN))
            }

            override fun onAdShowedFullScreenContent() {
            }

            override fun onAdImpression() {
            }
        }
    }

    fun showReward(): Single<String> {
        mRewardedAd?.show(appCompatActivity, onResult())
        return Single.create {
            singleEmitter = it
        }
    }

    private fun onResult(): OnUserEarnedRewardListener {
        return OnUserEarnedRewardListener {
            singleEmitter?.onSuccess(Constant.Success.REWARD_SUCCESS)
        }
    }
}