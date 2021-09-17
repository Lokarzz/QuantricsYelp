package com.lokarz.yelp.view.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.yelp.dagger.factory.ViewModelProviderFactory
import com.lokarz.yelp.model.repository.yelp.YelpRepository
import com.lokarz.yelp.util.AppPermission
import com.lokarz.yelp.util.StringResource
import com.lokarz.yelp.util.location.AppLocation
import com.lokarz.yelp.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class HomeActivityModule : BaseActivityModule<HomeActivity>() {


    @Provides
    fun provideAppPermission(context: Context): AppPermission {
        return AppPermission(context as AppCompatActivity)
    }


    @Provides
    fun provideAppLocation(context: Context, appPermission: AppPermission): AppLocation {
        return AppLocation(context as AppCompatActivity, appPermission)
    }

    @Provides
    fun provideFactory(
        yelpRepository: YelpRepository,
        appLocation: AppLocation,
        stringResource: StringResource,
    ): ViewModelProvider.Factory {
        val homeViewModel = HomeViewModel(yelpRepository, appLocation, stringResource)
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

}