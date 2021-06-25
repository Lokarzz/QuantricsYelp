package com.lokarz.gameforview.view.fragment.splash

import com.lokarz.gameforview.view.base.BaseFragmentModule
import dagger.Module


@Module
class SplashFragmentModule : BaseFragmentModule<SplashFragment>() {


//    @Provides
//    fun provideFactory(iGoogleService: IGoogleService): ViewModelProvider.Factory {
//        val splashViewModel = SplashViewModel(iGoogleService)
//        return ViewModelProviderFactory(splashViewModel);
//    }
//
//
//    @Provides
//    fun provideViewModel(
//        viewModelStoreOwner: ViewModelStoreOwner,
//        viewModelFactory: ViewModelProvider.Factory
//    ): SplashViewModel {
//        return ViewModelProvider(
//            viewModelStoreOwner,
//            viewModelFactory
//        ).get(SplashViewModel::class.java)
//    }

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