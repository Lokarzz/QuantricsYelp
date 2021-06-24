package com.lokarz.gameforview.view.base

import android.content.Context
import androidx.fragment.app.Fragment
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
    fun provideContext(t: T): Context? {
        return t.baseActivity
    }

    @Provides
    fun provideWeakReference(context: Context): WeakReference<Context> {
        return WeakReference(context);
    }
}