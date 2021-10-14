package com.lokarz.yelp.dagger.module

import com.lokarz.yelp.MainApplication
import com.lokarz.yelp.dagger.module.apiModule.ApiModule
import com.lokarz.yelp.helper.security.CryptographyHelper
import com.lokarz.yelp.helper.sharedPreference.SharedPreferenceHelper
import com.lokarz.yelp.model.api.retrofit.yelp.IYelpService
import com.lokarz.yelp.model.api.retrofit.yelp.YelpClient
import com.lokarz.yelp.model.api.retrofit.yelp.YelpInterceptor
import com.lokarz.yelp.model.repository.YelpLocalRepository
import com.lokarz.yelp.model.repository.YelpRemoteRepository
import com.lokarz.yelp.model.repository.YelpRepository
import com.lokarz.yelp.util.StringResource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideStringResource(mainApplication: MainApplication): StringResource {
        return StringResource(mainApplication)
    }

    @Singleton
    @Provides
    fun provideCryptographyHelper(mainApplication: MainApplication): CryptographyHelper {
        return CryptographyHelper(mainApplication)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceHelper(
        mainApplication: MainApplication,
        cryptographyHelper: CryptographyHelper
    ): SharedPreferenceHelper {
        return SharedPreferenceHelper(mainApplication, cryptographyHelper.masterKey)
    }

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