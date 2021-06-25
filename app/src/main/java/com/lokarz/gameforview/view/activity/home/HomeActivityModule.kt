package com.lokarz.gameforview.view.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.model.repository.google.GoogleLocalRepository
import com.lokarz.gameforview.model.repository.google.GoogleRemoteRepository
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import com.lokarz.gameforview.model.repository.profile.LocalProfileRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.util.Preference
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
    fun providedLocalProfileRepository(preference: Preference): LocalProfileRepository {
        return LocalProfileRepository(preference)
    }

    @Provides
    fun provideProfileRepository(localProfileRepository: LocalProfileRepository): ProfileRepository {
        return ProfileRepository(localProfileRepository)
    }

    @Provides
    fun provideRxGoogle(context: Context): RxGoogle {
        return RxGoogle(context as AppCompatActivity)
    }

    fun provideGoogleLocalRepository(preference: Preference): GoogleLocalRepository {
        return GoogleLocalRepository(preference)
    }

    @Provides
    fun provideGoogleRemoteRepository(rxGoogle: RxGoogle): GoogleRemoteRepository {
        return GoogleRemoteRepository(rxGoogle)
    }

    @Provides
    fun provideFactory(
        profileRepository: ProfileRepository,
        googleRepository: GoogleRepository
    ): ViewModelProvider.Factory {
        val homeViewModel = HomeViewModel(profileRepository, googleRepository)
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