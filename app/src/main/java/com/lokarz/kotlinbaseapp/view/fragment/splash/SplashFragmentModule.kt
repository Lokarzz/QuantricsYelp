package com.lokarz.kotlinbaseapp.view.fragment.splash

import android.content.Context
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.ads.MobileAds
import com.lokarz.kotlinbaseapp.dagger.factory.ViewModelProviderFactory
import com.lokarz.kotlinbaseapp.dagger.module.retrofit.IAppService
import com.lokarz.kotlinbaseapp.view.base.BaseFragmentModule
import com.lokarz.kotlinbaseapp.viewmodel.AdMobViewModel
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides


@Module
class SplashFragmentModule : BaseFragmentModule<SplashFragment>() {


    @Provides
    fun provideFactory(iAppService: IAppService): ViewModelProvider.Factory {
        val splashViewModel = SplashViewModel(iAppService)
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

    @Provides
    fun provideAdMobViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        @Nullable context: Context
    ): AdMobViewModel {
        val adMobViewModel = ViewModelProvider(viewModelStoreOwner).get(AdMobViewModel::class.java)
        MobileAds.initialize(context) {}
        adMobViewModel.initReward(context)
        return adMobViewModel
    }


}