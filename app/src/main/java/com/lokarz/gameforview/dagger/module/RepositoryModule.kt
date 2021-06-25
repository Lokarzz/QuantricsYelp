package com.lokarz.gameforview.dagger.module

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lokarz.gameforview.MainApplication
import com.lokarz.gameforview.model.api.retrofit.youtube.YoutubeClient
import com.lokarz.gameforview.model.api.retrofit.youtube.IYoutubeService
import com.lokarz.gameforview.util.Preference
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providePreference(mainApplication: MainApplication): Preference {
        return Preference(mainApplication)
    }

    @Provides
    fun provideService(): IYoutubeService {
        val googleClient = YoutubeClient()

        return googleClient.service!!
    }

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }


}