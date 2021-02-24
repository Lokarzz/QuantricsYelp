package com.lokarz.kotlinbaseapp.dagger.component

import com.lokarz.kotlinbaseapp.MainApplication
import com.lokarz.kotlinbaseapp.dagger.module.ActivityBuildersModule
import com.lokarz.kotlinbaseapp.dagger.module.FragmentBuildersModule
import com.lokarz.kotlinbaseapp.dagger.module.retrofit.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    modules = [AndroidInjectionModule::class, RetrofitModule::class, ActivityBuildersModule::class, FragmentBuildersModule::class]
)
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun inject(mainApplication: MainApplication): Builder

        fun build(): ApplicationComponent
    }

}