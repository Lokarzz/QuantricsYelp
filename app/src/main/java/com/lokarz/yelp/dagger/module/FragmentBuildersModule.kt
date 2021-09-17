package com.lokarz.yelp.dagger.module

import com.lokarz.yelp.view.fragment.restaurants.RestaurantFragment
import com.lokarz.yelp.view.fragment.restaurants.RestaurantFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = [RestaurantFragmentModule::class])
    abstract fun restaurantFragment(): RestaurantFragment



}