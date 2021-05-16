package com.lokarz.kotlinbaseapp.dagger.module

import com.lokarz.kotlinbaseapp.view.activity.home.HomeActivity
import com.lokarz.kotlinbaseapp.view.activity.home.HomeActivityModule
import com.lokarz.kotlinbaseapp.view.activity.splash.SplashActivity
import com.lokarz.kotlinbaseapp.view.activity.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): HomeActivity



}