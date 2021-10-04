package com.lokarz.yelp.view.activity.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.yelp.dagger.factory.ViewModelProviderFactory
import com.lokarz.yelp.helper.location.LocationHelper
import com.lokarz.yelp.helper.permission.PermissionHelper
import com.lokarz.yelp.model.repository.YelpRepository
import com.lokarz.yelp.util.StringResource
import com.lokarz.yelp.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class HomeActivityModule : BaseActivityModule<HomeActivity>() {


    @Provides
    fun provideAppPermission(context: Context): PermissionHelper {
        return PermissionHelper(context as AppCompatActivity)
    }


    @Provides
    fun provideAppLocation(context: Context, permissionHelper: PermissionHelper): LocationHelper {
        return LocationHelper(context as AppCompatActivity, permissionHelper)
    }

    @Provides
    fun provideFactory(
        yelpRepository: YelpRepository,
        locationHelper: LocationHelper,
        stringResource: StringResource,
    ): ViewModelProvider.Factory {
        val homeViewModel = HomeViewModel(yelpRepository, locationHelper, stringResource)
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