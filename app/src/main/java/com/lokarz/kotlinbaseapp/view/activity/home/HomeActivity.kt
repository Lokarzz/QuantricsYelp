package com.lokarz.kotlinbaseapp.view.activity.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.ActivityHomeBinding
import com.lokarz.kotlinbaseapp.view.base.BaseActivity
import com.lokarz.kotlinbaseapp.view.fragment.youtube.YoutubeFragment

class HomeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )
        activityHomeBinding.lifecycleOwner = this

        initFragment()

    }

    private fun initFragment() {
        goToFragment(YoutubeFragment.newInstance(), R.id.fl)
    }

}