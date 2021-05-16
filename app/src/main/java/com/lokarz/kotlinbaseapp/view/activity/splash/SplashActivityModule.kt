package com.lokarz.kotlinbaseapp.view.activity.splash

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.kotlinbaseapp.dagger.factory.ViewModelProviderFactory
import com.lokarz.kotlinbaseapp.dagger.module.retrofit.IAppService
import com.lokarz.kotlinbaseapp.view.base.BaseActivityModule
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule : BaseActivityModule<SplashActivity>() {



    @Provides
    fun provideFactory(iAppService: IAppService) : ViewModelProvider.Factory{
        val splashViewModel = SplashViewModel(iAppService)
        return ViewModelProviderFactory(splashViewModel);
    }


    @Provides
    fun provideSplashViewModel(viewModelStoreOwner: ViewModelStoreOwner, viewModelProvider:ViewModelProvider.Factory): SplashViewModel {
        return ViewModelProvider(viewModelStoreOwner, viewModelProvider).get(SplashViewModel::class.java)
    }

//    @Provides
//    fun provideAdMobViewModel(viewModelStoreOwner: ViewModelStoreOwner, splashActivity: SplashActivity): AdMobViewModel {
//        val adMobViewModel = ViewModelProvider(viewModelStoreOwner).get(AdMobViewModel::class.java)
//        MobileAds.initialize(splashActivity) {}
//        adMobViewModel.initReward(splashActivity)
//        return adMobViewModel
//    }

}