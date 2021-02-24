package com.lokarz.kotlinbaseapp.dagger.module

import com.lokarz.kotlinbaseapp.view.activity.splash.SplashActivity
import com.lokarz.kotlinbaseapp.view.activity.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivity(): SplashActivity



}