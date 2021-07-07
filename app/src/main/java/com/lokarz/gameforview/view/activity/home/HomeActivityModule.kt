package com.lokarz.gameforview.view.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.model.repository.google.GoogleLocalRepository
import com.lokarz.gameforview.model.repository.google.GoogleRemoteRepository
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import com.lokarz.gameforview.model.repository.profile.ProfileLocalRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.util.Preference
import com.lokarz.gameforview.model.util.RxGoogle
import com.lokarz.gameforview.view.base.BaseActivityModule
import com.lokarz.gameforview.model.util.AdMob
import dagger.Module
import dagger.Provides


@Module
class HomeActivityModule : BaseActivityModule<HomeActivity>() {

    @Provides
    fun providedLocalProfileRepository(preference: Preference): ProfileLocalRepository {
        return ProfileLocalRepository(preference)
    }

    @Provides
    fun provideProfileRepository(profileLocalRepository: ProfileLocalRepository): ProfileRepository {
        return ProfileRepository(profileLocalRepository)
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
    fun provideGoogleRepository(
        googleLocalRepository: GoogleLocalRepository,
        googleRemoteRepository: GoogleRemoteRepository
    ): GoogleRepository {
        return GoogleRepository(googleLocalRepository, googleRemoteRepository)
    }

    @Provides
    fun provideAdMob(context: Context) : AdMob {
        return AdMob(context as AppCompatActivity)
    }

    @Provides
    fun provideFactory(
        profileRepository: ProfileRepository,
        googleRepository: GoogleRepository,
        adMob: AdMob
    ): ViewModelProvider.Factory {
        val homeViewModel = HomeViewModel(profileRepository, googleRepository,adMob)
        return ViewModelProviderFactory(homeViewModel)
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

//    @Provides
//    @Named("AdMobFactory")
//    fun provideAdMobFactory(context: Context): ViewModelProvider.Factory {
//        val adMobViewModel = AdMobViewModel(context)
//        return ViewModelProviderFactory(adMobViewModel);
//    }
//
//    @Provides
//    fun provideAdMobViewModel(
//        viewModelStoreOwner: ViewModelStoreOwner,
//        @Named("AdMobFactory") viewModelProvider: ViewModelProvider.Factory,
//    ): AdMobViewModel {
//        return ViewModelProvider(
//            viewModelStoreOwner,
//            viewModelProvider
//        ).get(AdMobViewModel::class.java)
//    }


}