package com.lokarz.yelp.view.activity.splash

import com.lokarz.yelp.view.base.BaseActivityModule
import dagger.Module


@Module
class SplashActivityModule : BaseActivityModule<SplashActivity>() {


//    @Provides
//    fun provideFactory(): ViewModelProvider.Factory {
//        val splashViewModel = SplashViewModel()
//        return ViewModelProviderFactory(splashViewModel)
//    }
//
//    @Provides
//    fun provideSplashViewModel(
//        viewModelStoreOwner: ViewModelStoreOwner,
//        viewModelProvider: ViewModelProvider.Factory
//    ): SplashViewModel {
//        return ViewModelProvider(
//            viewModelStoreOwner,
//            viewModelProvider
//        ).get(SplashViewModel::class.java)
//    }
}