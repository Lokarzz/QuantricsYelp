package com.lokarz.gameforview

import com.lokarz.gameforview.dagger.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


class MainApplication : DaggerApplication() {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().inject(this).build()

    }
}