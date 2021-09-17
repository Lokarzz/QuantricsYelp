package com.lokarz.yelp.view.base

import android.content.Context
import androidx.lifecycle.ViewModelStoreOwner
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference


@Module
abstract class BaseFragmentModule<T : BaseFragment> {

    @Provides
    fun provideViewModelStoreOwner(t: T): ViewModelStoreOwner {
        return t
    }

    @Provides
    fun provideWeakReference(context: Context): WeakReference<Context> {
        return WeakReference(context)
    }
}