package com.lokarz.gameforview.view.activity.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.model.repository.google.GoogleLocalRepository
import com.lokarz.gameforview.model.repository.google.GoogleRemoteRepository
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import com.lokarz.gameforview.util.Preference
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class LoginActivityModule : BaseActivityModule<LoginActivity>() {

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
        googleRemoteRepository: GoogleRemoteRepository, googleLocalRepository: GoogleLocalRepository
    ): GoogleRepository {
        return GoogleRepository(googleLocalRepository, googleRemoteRepository)
    }

    @Provides
    fun provideFactory(googleRepository: GoogleRepository): ViewModelProvider.Factory {
        val loginViewModel = LoginViewModel(googleRepository)
        return ViewModelProviderFactory(loginViewModel);
    }

    @Provides
    fun provideSplashViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelProvider: ViewModelProvider.Factory
    ): LoginViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(LoginViewModel::class.java)
    }


}