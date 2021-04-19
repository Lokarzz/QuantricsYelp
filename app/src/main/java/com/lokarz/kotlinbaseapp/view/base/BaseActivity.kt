package com.lokarz.kotlinbaseapp.view.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lokarz.kotlinbaseapp.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity()  {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any?>;

    @Inject
    lateinit var weakReference: WeakReference<Context>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
    }

    fun androidInjector(): AndroidInjector<Any?> {
        return androidInjector
    }

    fun replaceFragment(fragment: Fragment?, @IdRes container: Int = R.id.fragment) {
        val fm = supportFragmentManager
        try {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val ft = fm.beginTransaction()
            ft.replace(container, fragment!!)
            ft.commit()
        } catch (e: Exception) {
            // no nothing
        }
    }

    open fun addFragment(fragment: Fragment?, @IdRes container: Int = R.id.fragment) {
        val fm = supportFragmentManager
        try {
            val ft = fm.beginTransaction()
            if (fragment != null) {
                ft.replace(container, fragment)
                ft.addToBackStack(fragment.javaClass.simpleName)
                ft.commit()
            }
        } catch (e: java.lang.Exception) {
            // no nothing
        }
    }
}