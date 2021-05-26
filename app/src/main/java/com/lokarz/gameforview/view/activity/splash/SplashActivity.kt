package com.lokarz.gameforview.view.activity.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivitySplashBinding
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.base.BaseActivity
import com.lokarz.gameforview.viewmodel.SplashViewModel
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