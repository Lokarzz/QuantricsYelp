package com.lokarz.yelp.dagger.module

import com.lokarz.yelp.dagger.module.helper.HelperModule
import com.lokarz.yelp.helper.security.CryptographyHelper
import com.lokarz.yelp.helper.sharedPreference.SharedPreferenceHelper
import com.lokarz.yelp.model.api.retrofit.yelp.IYelpService
import com.lokarz.yelp.model.api.retrofit.yelp.YelpClient
import com.lokarz.yelp.model.api.retrofit.yelp.YelpInterceptor
import com.lokarz.yelp.model.repository.YelpLocalRepository
import com.lokarz.yelp.model.repository.YelpRemoteRepository
import com.lokarz.yelp.model.repository.YelpRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [HelperModule::class])
class AppModule {


    @Singleton
    @Provides
    fun provideYelpInterceptor(cryptographyHelper: CryptographyHelper): YelpInterceptor {
        return YelpInterceptor(cryptographyHelper.getKeyData().yelpApiKey)
    }

    @Singleton
    @Provides
    fun provideIYelpService(yelpInterceptor: YelpInterceptor): IYelpService {
        return YelpClient(yelpInterceptor).service
    }

    @Singleton
    @Provides
    fun provideYelpRemoteRepository(iYelpService: IYelpService): YelpRemoteRepository {
        return YelpRemoteRepository(iYelpService)
    }

    @Singleton
    @Provides
    fun provideYelpLocalRepository(sharedPreferenceHelper: SharedPreferenceHelper): YelpLocalRepository {
        return YelpLocalRepository(sharedPreferenceHelper)
    }

    @Singleton
    @Provides
    fun provideYelpRepository(
        yelpRemoteRepository: YelpRemoteRepository,
        yelpLocalRepository: YelpLocalRepository
    ): YelpRepository {
        return YelpRepository(yelpRemoteRepository, yelpLocalRepository)
    }

}