package com.lokarz.kotlinbaseapp.dagger.component

import com.lokarz.kotlinbaseapp.MainApplication
import com.lokarz.kotlinbaseapp.module.ActivityBuildersModule
import com.lokarz.kotlinbaseapp.module.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [AndroidInjectionModule::class, ApiModule::class, ActivityBuildersModule::class])
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder interface Builder{

        @BindsInstance
        fun inject (mainApplication: MainApplication) : Builder

        fun build() : ApplicationComponent
    }

}