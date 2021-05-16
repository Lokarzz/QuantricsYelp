package com.lokarz.kotlinbaseapp.view.base

import android.content.Context
import android.os.Bundle
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

    lateinit var baseActivity: BaseActivity;

    override fun onCreate(savedInstanceState: Bundle?) {
        baseActivity = context as BaseActivity;
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }



    override fun androidInjector(): AndroidInjector<Any?>? {
        return androidInjector
    }
}