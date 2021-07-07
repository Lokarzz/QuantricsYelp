package com.lokarz.gameforview.view.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lokarz.gameforview.util.FragmentUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any?>

    @Inject
    lateinit var weakReference: WeakReference<Context>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    fun androidInjector(): AndroidInjector<Any?> {
        return androidInjector
    }

    fun popFragment(baseFragment: BaseFragment, resId: Int) {
        FragmentUtil.popFragment(supportFragmentManager, baseFragment, resId)
    }

    fun goToFragment(baseFragment: BaseFragment, resId: Int) {
        FragmentUtil.goToFragment(supportFragmentManager, baseFragment, resId)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

}