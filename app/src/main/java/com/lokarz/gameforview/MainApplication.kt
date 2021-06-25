package com.lokarz.gameforview

import android.util.Log
import com.lokarz.gameforview.dagger.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.rxjava3.plugins.RxJavaPlugins


class MainApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler {
            Log.e("RxJavaPlugins", "${it.message}")
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().inject(this).build()
    }
}