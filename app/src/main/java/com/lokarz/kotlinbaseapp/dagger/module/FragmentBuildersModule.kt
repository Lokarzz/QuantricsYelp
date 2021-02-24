package com.lokarz.kotlinbaseapp.dagger.module

import com.lokarz.kotlinbaseapp.view.fragment.SplashFragment
import com.lokarz.kotlinbaseapp.view.fragment.SplashFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector(modules = [SplashFragmentModule::class])
    abstract fun splashFragment(): SplashFragment


}