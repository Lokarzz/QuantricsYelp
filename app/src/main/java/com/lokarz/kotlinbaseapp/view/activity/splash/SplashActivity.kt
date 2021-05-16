package com.lokarz.kotlinbaseapp.view.activity.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.ActivitySplashBinding
import com.lokarz.kotlinbaseapp.util.ActivityUtil
import com.lokarz.kotlinbaseapp.view.activity.home.HomeActivity
import com.lokarz.kotlinbaseapp.view.base.BaseActivity
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity() {


    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activitySplashBinding = DataBindingUtil.setContentView<ActivitySplashBinding>(
            this,
            R.layout.activity_splash
        )
        activitySplashBinding.splashViewModel = splashViewModel
        activitySplashBinding.lifecycleOwner = this

        initViewModels()

        processLogin()

    }

    private fun initViewModels() {
        splashViewModel.init()
    }

    private fun processLogin() {
        splashViewModel.processLogin()
        splashViewModel.loginStatus?.observe(this) { status ->
            when (status) {
                "success" -> {
                    ActivityUtil.goToScreen(this, HomeActivity::class.java)
                }
            }
        }
    }


}