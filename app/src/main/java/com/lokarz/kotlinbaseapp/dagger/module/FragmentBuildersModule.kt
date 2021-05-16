package com.lokarz.kotlinbaseapp.dagger.module

import com.lokarz.kotlinbaseapp.view.fragment.splash.SplashFragment
import com.lokarz.kotlinbaseapp.view.fragment.splash.SplashFragmentModule
import com.lokarz.kotlinbaseapp.view.fragment.youtube.YoutubeFragment
import com.lokarz.kotlinbaseapp.view.fragment.youtube.YoutubeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    abstract fun splashFragment(): SplashFragment


    @ContributesAndroidInjector(modules = [YoutubeFragmentModule::class])
    abstract fun youtubeFragment(): YoutubeFragment


}