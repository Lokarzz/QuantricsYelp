package com.lokarz.yelp.dagger.component

import com.lokarz.yelp.MainApplication
import com.lokarz.yelp.dagger.module.ActivityBuildersModule
import com.lokarz.yelp.dagger.module.FragmentBuildersModule
import com.lokarz.yelp.dagger.module.ModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ModelModule::class,
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