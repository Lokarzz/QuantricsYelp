package com.lokarz.kotlinbaseapp.view.base

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasAndroidInjector{

    protected lateinit var mView: View;

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any?>

    protected lateinit var baseActivity: BaseActivity;


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        baseActivity = context as BaseActivity;
    }

    override fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }
}