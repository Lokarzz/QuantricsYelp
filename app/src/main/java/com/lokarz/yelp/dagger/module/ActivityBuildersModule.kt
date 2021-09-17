package com.lokarz.yelp.dagger.module

import com.lokarz.yelp.view.activity.businessdetails.BusinessDetailsActivity
import com.lokarz.yelp.view.activity.businessdetails.BusinessDetailsActivityModule
import com.lokarz.yelp.view.activity.home.HomeActivity
import com.lokarz.yelp.view.activity.home.HomeActivityModule
import com.lokarz.yelp.view.activity.splash.SplashActivity
import com.lokarz.yelp.view.activity.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [BusinessDetailsActivityModule::class])
    abstract fun businessDetailsActivity(): BusinessDetailsActivity

}