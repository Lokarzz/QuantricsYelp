package com.lokarz.kotlinbaseapp.view.activity.splash

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.DataBindingUtil
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.ActivitySplashBinding
import com.lokarz.kotlinbaseapp.view.base.BaseActivity
import com.lokarz.kotlinbaseapp.view.fragment.SplashFragment
import com.lokarz.kotlinbaseapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    lateinit var et: AppCompatEditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activitySplashBinding = DataBindingUtil.setContentView<ActivitySplashBinding>(
            this,
            R.layout.activity_splash
        )
        activitySplashBinding.splashViewModel = splashViewModel
        activitySplashBinding.lifecycleOwner = this

        splashViewModel.init()
        et = findViewById(R.id.et_text)
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                splashViewModel.title?.value = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        replaceFragment(SplashFragment.newInstance())
    }
}