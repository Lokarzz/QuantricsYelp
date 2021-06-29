package com.lokarz.gameforview.view.activity.addYoutube

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.firebase.firestore.FirebaseFirestore
import com.lokarz.gameforview.dagger.factory.ViewModelProviderFactory
import com.lokarz.gameforview.model.api.retrofit.youtube.IYoutubeService
import com.lokarz.gameforview.model.repository.firebase.FirebaseRemoteRepository
import com.lokarz.gameforview.model.repository.firebase.FirebaseRepository
import com.lokarz.gameforview.model.repository.google.GoogleLocalRepository
import com.lokarz.gameforview.model.repository.google.GoogleRemoteRepository
import com.lokarz.gameforview.model.repository.google.GoogleRepository
import com.lokarz.gameforview.model.repository.profile.ProfileLocalRepository
import com.lokarz.gameforview.model.repository.profile.ProfileRepository
import com.lokarz.gameforview.model.repository.youtube.YoutubeRemoteRepository
import com.lokarz.gameforview.model.repository.youtube.YoutubeRepository
import com.lokarz.gameforview.util.Preference
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class AddYoutubeModule : BaseActivityModule<AddYoutubeActivity>() {

    @Provides
    fun provideFirebaseRemoteRepository(firebaseFirestore: FirebaseFirestore): FirebaseRemoteRepository {
        return FirebaseRemoteRepository(firebaseFirestore)
    }

    @Provides
    fun provideFirebaseRepository(firebaseRemoteRepository: FirebaseRemoteRepository): FirebaseRepository {
        return FirebaseRepository(firebaseRemoteRepository)
    }

    @Provides
    fun providedLocalProfileRepository(preference: Preference): ProfileLocalRepository {
        return ProfileLocalRepository(preference)
    }

    @Provides
    fun provideProfileRepository(profileLocalRepository: ProfileLocalRepository): ProfileRepository {
        return ProfileRepository(profileLocalRepository)
    }

    fun provideGoogleLocalRepository(preference: Preference): GoogleLocalRepository {
        return GoogleLocalRepository(preference)
    }

    @Provides
    fun provideRxGoogle(context: Context): RxGoogle {
        return RxGoogle(context as AppCompatActivity)
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
    fun provideYoutubeRemoteRepository(iYoutubeService: IYoutubeService):YoutubeRemoteRepository{
        return YoutubeRemoteRepository(iYoutubeService)
    }

    @Provides
    fun provideYoutubeRepository(youtubeRemoteRepository: YoutubeRemoteRepository) : YoutubeRepository{
        return YoutubeRepository(youtubeRemoteRepository)
    }

    @Provides
    fun provideFactory(
        googleRepository: GoogleRepository,
        profileRepository: ProfileRepository,
        firebaseRepository: FirebaseRepository,
        youtubeRepository: YoutubeRepository
    ): ViewModelProvider.Factory {
        val addYoutubeViewModel = AddYoutubeViewModel(
            youtubeRepository,
            firebaseRepository,
            profileRepository,
            googleRepository
        )
        return ViewModelProviderFactory(addYoutubeViewModel)
    }

    @Provides
    fun provideAddYoutubeViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelProvider: ViewModelProvider.Factory
    ): AddYoutubeViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(AddYoutubeViewModel::class.java)
    }


}