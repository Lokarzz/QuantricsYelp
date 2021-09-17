package com.lokarz.yelp.view.activity.businessdetails

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.yelp.dagger.factory.ViewModelProviderFactory
import com.lokarz.yelp.model.repository.yelp.YelpRepository
import com.lokarz.yelp.util.StringResource
import com.lokarz.yelp.view.base.BaseActivityModule
import dagger.Module
import dagger.Provides


@Module
class BusinessDetailsActivityModule : BaseActivityModule<BusinessDetailsActivity>() {

    @Provides
    fun provideFactory(
        yelpRepository: YelpRepository,
        stringResource: StringResource
    ): ViewModelProvider.Factory {
        val homeViewModel = BusinessDetailsViewModel(yelpRepository, stringResource)
        return ViewModelProviderFactory(homeViewModel)
    }

    @Provides
    fun provideHomeViewModel(
        viewModelStoreOwner: ViewModelStoreOwner,
        viewModelProvider: ViewModelProvider.Factory
    ): BusinessDetailsViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelProvider
        ).get(BusinessDetailsViewModel::class.java)
    }

}