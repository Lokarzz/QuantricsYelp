package com.lokarz.gameforview.view.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelStoreOwner
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference


@Module
abstract class BaseActivityModule<T : AppCompatActivity> {

    @Provides
    fun provideViewModelStoreOwner(t: T): ViewModelStoreOwner {
        return t;
    }

    @Provides
    fun provideContext(t: T): Context {
        return t;
    }

    @Provides
    fun provideWeakReference(context: Context): WeakReference<Context> {
        return WeakReference(context);
    }
}