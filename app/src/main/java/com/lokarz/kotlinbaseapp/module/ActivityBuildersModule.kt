package com.lokarz.kotlinbaseapp.module

import com.lokarz.kotlinbaseapp.view.activites.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

}