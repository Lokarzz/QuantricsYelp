package com.lokarz.yelp.dagger.module

import com.lokarz.yelp.MainApplication
import com.lokarz.yelp.model.api.retrofit.yelp.IYelpService
import com.lokarz.yelp.model.api.retrofit.yelp.YelpClient
import com.lokarz.yelp.model.repository.YelpRemoteRepository
import com.lokarz.yelp.model.repository.YelpRepository
import com.lokarz.yelp.util.StringResource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModelModule {

    @Singleton
    @Provides
    fun provideStringResource(mainApplication: MainApplication): StringResource {
        return StringResource(mainApplication)
    }


    @Singleton
    @Provides
    fun provideIYelpService(): IYelpService {
        val yelpClient = YelpClient()

        return yelpClient.service
    }

    @Singleton
    @Provides
    fun provideYelpRemoteRepository(iYelpService: IYelpService): YelpRemoteRepository {
        return YelpRemoteRepository(iYelpService)
    }

    @Singleton
    @Provides
    fun provideYelpYelpRepository(yelpRemoteRepository: YelpRemoteRepository): YelpRepository {
        return YelpRepository(yelpRemoteRepository)
    }


//
//    @Singleton
//    @Provides
//    fun provideFirebaseFirestore(): FirebaseFirestore {
//        return Firebase.firestore
//    }


}