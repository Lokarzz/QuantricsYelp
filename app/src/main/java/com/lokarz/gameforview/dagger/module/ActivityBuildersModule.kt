package com.lokarz.gameforview.dagger.module

import com.lokarz.gameforview.view.activity.addYoutube.AddYoutubeActivity
import com.lokarz.gameforview.view.activity.addYoutube.AddYoutubeModule
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.activity.home.HomeActivityModule
import com.lokarz.gameforview.view.activity.login.LoginActivity
import com.lokarz.gameforview.view.activity.login.LoginActivityModule
import com.lokarz.gameforview.view.activity.splash.SplashActivity
import com.lokarz.gameforview.view.activity.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun homeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun loginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [AddYoutubeModule::class])
    abstract fun addYoutubeActivity(): AddYoutubeActivity

}