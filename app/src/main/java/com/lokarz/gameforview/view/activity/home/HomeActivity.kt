package com.lokarz.gameforview.view.activity.home

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivityHomeBinding
import com.lokarz.gameforview.view.base.BaseActivity
import com.lokarz.gameforview.view.fragment.youtube.YoutubeFragment

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