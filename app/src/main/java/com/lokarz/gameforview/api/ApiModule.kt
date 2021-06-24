package com.lokarz.gameforview.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lokarz.gameforview.api.retrofit.google.GoogleClient
import com.lokarz.gameforview.api.retrofit.google.IGoogleService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {


    @Provides
    fun provideService(): IGoogleService {
        val googleClient = GoogleClient()

        return googleClient.service!!
    }

    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore{
        return Firebase.firestore
    }


}