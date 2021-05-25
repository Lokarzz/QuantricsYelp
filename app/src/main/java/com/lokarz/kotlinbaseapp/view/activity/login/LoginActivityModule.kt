package com.lokarz.kotlinbaseapp.view.activity.login

import com.lokarz.kotlinbaseapp.view.base.BaseActivityModule
import dagger.Module


@Module
class LoginActivityModule : BaseActivityModule<LoginActivity>() {



//    @Provides
//    fun provideFactory(iAppService: IAppService) : ViewModelProvider.Factory{
//        val splashViewModel = SplashViewModel(iAppService)
//        return ViewModelProviderFactory(splashViewModel);
//    }
//
//    @Provides
//    fun provideSplashViewModel(viewModelStoreOwner: ViewModelStoreOwner, viewModelProvider:ViewModelProvider.Factory): SplashViewModel {
//        return ViewModelProvider(viewModelStoreOwner, viewModelProvider).get(SplashViewModel::class.java)
//    }


}