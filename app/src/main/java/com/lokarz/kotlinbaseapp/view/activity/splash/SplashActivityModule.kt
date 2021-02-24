package com.lokarz.kotlinbaseapp.view.activity.splash

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.kotlinbaseapp.view.base.BaseActivityModule
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides


@Module
class SplashActivityModule : BaseActivityModule<SplashActivity>() {

    @Provides
    fun provideViewModel(viewModelStoreOwner: ViewModelStoreOwner): SplashViewModel {
        return ViewModelProvider(viewModelStoreOwner).get(SplashViewModel::class.java)
    }


}