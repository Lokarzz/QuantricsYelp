package com.lokarz.gameforview.view.activity.splash

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.api.retrofit.google.IGoogleService
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule : BaseActivityModule<SplashActivity>() {

    @Provides
    fun provide(context: Context) : RxGoogle {
        return RxGoogle(context as AppCompatActivity)
    }


    @Provides
    fun providePreferenceUtil(context: Context): PreferenceUtil {
        return PreferenceUtil(context)
    }

    @Provides
    fun provideFactory(iGoogleService: IGoogleService): ViewModelProvider.Factory {
        val splashViewModel = SplashViewModel(iGoogleService)
        return ViewModelProviderFactory(splashViewModel);
    }


    @Provides
    fun provideSplashViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelProvider: ViewModelProvider.Factory
    ): SplashViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(SplashViewModel::class.java)
    }

//    @Provides
//    fun provideAdMobViewModel(viewModelStoreOwner: ViewModelStoreOwner, splashActivity: SplashActivity): AdMobViewModel {
//        val adMobViewModel = ViewModelProvider(viewModelStoreOwner).get(AdMobViewModel::class.java)
//        MobileAds.initialize(splashActivity) {}
//        adMobViewModel.initReward(splashActivity)
//        return adMobViewModel
//    }

}