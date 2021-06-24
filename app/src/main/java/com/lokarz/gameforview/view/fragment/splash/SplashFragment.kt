package com.lokarz.gameforview.view.fragment.splash

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import com.google.android.gms.ads.AdView
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.FragmentSplashBinding
import com.lokarz.gameforview.view.base.BaseFragment
import com.lokarz.gameforview.viewmodel.AdMobViewModel
import com.lokarz.gameforview.view.activity.splash.SplashViewModel
import javax.inject.Inject

class SplashFragment : BaseFragment() {


    @Inject
    lateinit var splashViewModel: SplashViewModel;

//    @Inject
//    lateinit var adMobViewModel: AdMobViewModel

    lateinit var adView: AdView;

    lateinit var et: AppCompatEditText;

    companion object {
        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentSplashBinding = DataBindingUtil.inflate<FragmentSplashBinding>(
            inflater,
            R.layout.fragment_splash,
            container,
            false
        )
        fragmentSplashBinding.splashViewModel = splashViewModel
        fragmentSplashBinding.lifecycleOwner = this

        mView = fragmentSplashBinding.root


//        initAds()
//        initEtText()

        return mView
    }

//    fun initEtText() {
//        et = mView.findViewById(R.id.et_text)
//        et.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, aftaer: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                splashViewModel.title?.value = s.toString()
//                adMobViewModel.showReward(baseActivity) { rewardItem -> Log.w("rewardItem", rewardItem.toString()) }
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//    }
//
//    fun initAds() {
//        adView = mView.findViewById(R.id.adView)
//        adView.loadAd(adMobViewModel.getAdRequest())
//
//    }


}