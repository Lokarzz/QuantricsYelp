package com.lokarz.yelp.view.fragment.restaurants

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.lokarz.yelp.dagger.factory.ViewModelProviderFactory
import com.lokarz.yelp.model.repository.yelp.YelpRepository
import com.lokarz.yelp.view.base.BaseFragmentModule
import dagger.Module
import dagger.Provides


@Module
class RestaurantFragmentModule : BaseFragmentModule<RestaurantFragment>() {


    @Provides
    fun provideFactory(yelpRepository: YelpRepository) : ViewModelProvider.Factory {
        val splashViewModel = RestaurantViewModel(yelpRepository)
        return ViewModelProviderFactory(splashViewModel);
    }

    @Provides
    fun provideViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    viewModelFactory: ViewModelProvider.Factory
    ): RestaurantViewModel {
        return ViewModelProvider(
            viewModelStoreOwner,
            viewModelFactory
        ).get(RestaurantViewModel::class.java)
    }

//    @Provides
//    fun provideAdMobViewModel(
//        viewModelStoreOwner: ViewModelStoreOwner,
//        @Nullable context: Context
//    ): AdMobViewModel {
//        val adMobViewModel = ViewModelProvider(viewModelStoreOwner).get(AdMobViewModel::class.java)
//        MobileAds.initialize(context) {}
//        return adMobViewModel
//    }


}