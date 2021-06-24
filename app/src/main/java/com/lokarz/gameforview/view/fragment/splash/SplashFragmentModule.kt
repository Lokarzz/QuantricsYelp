package com.lokarz.gameforview.view.fragment.splash

import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.ads.MobileAds
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.api.retrofit.google.IGoogleService
import com.lokarz.gameforview.view.base.BaseFragmentModule
import com.lokarz.gameforview.viewmodel.AdMobViewModel
import com.lokarz.gameforview.view.activity.splash.SplashViewModel
import dagger.Module
import dagger.Provides


@Module
class SplashFragmentModule : BaseFragmentModule<SplashFragment>() {


    @Provides
    fun provideFactory(iGoogleService: IGoogleService): ViewModelProvider.Factory {
        val splashViewModel = SplashViewModel(iGoogleService)
        return ViewModelProviderFactory(splashViewModel);
    }


    @Provides
    fun provideViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelFactory: ViewModelProvider.Factory
    ): SplashViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelFactory
        ).get(SplashViewModel::class.java)
    }

//    @Provides
//    fun provideAdMobViewModel(
//        viewModelStoreOwner: ViewModelStoreOwner,
//        @Nullable context: Context
//    ): AdMobViewModel {
//        val adMobViewModel = ViewModelProvider(viewModelStoreOwner).get(AdMobViewModel::class.java)
//        MobileAds.initialize(context) {}
//        return adMobViewModel
//    }


}