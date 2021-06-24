package com.lokarz.gameforview.dagger.component

import com.lokarz.gameforview.MainApplication
import com.lokarz.gameforview.api.ApiModule
import com.lokarz.gameforview.dagger.module.ActivityBuildersModule
import com.lokarz.gameforview.dagger.module.FragmentBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    modules = [
        AndroidInjectionModule::class,
        ApiModule::class,
        ActivityBuildersModule::class,
        FragmentBuildersModule::class]
)
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun inject(mainApplication: MainApplication): Builder

        fun build(): ApplicationComponent
    }

}