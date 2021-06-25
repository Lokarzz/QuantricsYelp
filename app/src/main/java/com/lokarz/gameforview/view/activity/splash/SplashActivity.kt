package com.lokarz.gameforview.view.activity.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivitySplashBinding
import com.lokarz.gameforview.pojo.google.GoogleAccount
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.base.BaseActivity
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

        processLogin()

    }


    private fun processLogin() {
        splashViewModel.googleLogin().observe(this) {
            if (it == true) {
                ActivityUtil.gotoScreen(this, HomeActivity::class.java)
            }
        }
    }

}