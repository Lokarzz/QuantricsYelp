package com.lokarz.gameforview.view.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.base.BaseActivityModule
import com.lokarz.gameforview.viewmodel.AdMobViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
class HomeActivityModule : BaseActivityModule<HomeActivity>() {

    @Provides
    fun provide(context: Context): RxGoogle {
        return RxGoogle(context as AppCompatActivity)
    }

    @Provides
    fun providePreferenceUtil(context: Context): PreferenceUtil {
        return PreferenceUtil(context)
    }

    @Provides
    fun provideFactory(
        preferenceUtil: PreferenceUtil,
        rxGoogle: RxGoogle
    ): ViewModelProvider.Factory {
        val homeViewModel = HomeViewModel(preferenceUtil, rxGoogle)
        return ViewModelProviderFactory(homeViewModel);
    }

    @Provides
    fun provideHomeViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelProvider: ViewModelProvider.Factory
    ): HomeViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(HomeViewModel::class.java)
    }

    @Provides
    @Named("AdMobFactory")
    fun provideAdMobFactory(context: Context): ViewModelProvider.Factory {
        val adMobViewModel = AdMobViewModel(context)
        return ViewModelProviderFactory(adMobViewModel);
    }

    @Provides
    fun provideAdMobViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        @Named("AdMobFactory") viewModelProvider: ViewModelProvider.Factory,
    ): AdMobViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(AdMobViewModel::class.java)
    }


}