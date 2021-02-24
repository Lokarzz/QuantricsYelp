package com.lokarz.kotlinbaseapp.view.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import com.lokarz.kotlinbaseapp.view.base.BaseFragmentModule
import dagger.Module
import dagger.Provides


@Module
class SplashFragmentModule : BaseFragmentModule<SplashFragment>() {

    @Provides
    fun provideViewModel(viewModelStoreOwner: ViewModelStoreOwner): SplashViewModel {
        return ViewModelProvider(viewModelStoreOwner).get(SplashViewModel::class.java)
    }

}