package com.lokarz.yelp.dagger.module.helper

import com.lokarz.yelp.MainApplication
import com.lokarz.yelp.helper.security.CryptographyHelper
import com.lokarz.yelp.helper.sharedPreference.SharedPreferenceHelper
import com.lokarz.yelp.helper.resource.ResourceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HelperModule {

    @Singleton
    @Provides
    fun provideStringResource(mainApplication: MainApplication): ResourceHelper {
        return ResourceHelper(mainApplication)
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
}