package com.lokarz.gameforview.view.activity.home

import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivityHomeBinding
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.util.Constant
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.view.activity.addYoutube.AddYoutubeActivity
import com.lokarz.gameforview.view.activity.login.LoginActivity
import com.lokarz.gameforview.view.base.BaseActivity
import com.lokarz.gameforview.view.fragment.youtube.YoutubeFragment
import com.lokarz.gameforview.viewmodel.AdMobViewModel
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    private var drawerLayout: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private var navigationView: NavigationView? = null

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var adMobViewModel: AdMobViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )
        activityHomeBinding.homeViewModel = homeViewModel
        activityHomeBinding.lifecycleOwner = this

        initNavigation()
        initFragment()
        initRewardItem()

        setNavigationOnClickListener(
            R.id.vg_add_youtube_video,
            R.id.iv_help,
            R.id.btn_watch_add,
            R.id.vg_logout
        )

    }

    private fun initRewardItem() {
        adMobViewModel.rewardItem.observe(this) {
            when (it) {
                Constant.Success.REWARD_SUCCESS -> {
                    homeViewModel.addRewardPoints()
                }
                else -> {
                    showToast("Ads Already Shown this day")
                }
            }
        }
    }


    private fun setNavigationOnClickListener(vararg ids: Int) {
        for (id in ids) {
            findViewById<View>(id).setOnClickListener(getOnClickListener())
        }
    }

    private fun getOnClickListener(): View.OnClickListener {
        return View.OnClickListener {
            when (it.id) {
                R.id.vg_add_youtube_video -> {
                    ActivityUtil.popUpScreen(this, AddYoutubeActivity::class.java)
                }
                R.id.vg_logout -> {
                    homeViewModel.googleLogout().observe(this) {
                        ActivityUtil.gotoScreen(this, LoginActivity::class.java)
                    }
                }
                R.id.iv_help -> {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.game_points_hint_title)
                        .setMessage(R.string.game_points_hint_message)
                        .show()
                }
                R.id.btn_watch_add -> {
                    adMobViewModel.showReward(this)
                }
            }
            drawerLayout?.closeDrawer(GravityCompat.START)
        }
    }

    private fun initNavigation() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigation)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout?.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

    }

    private fun initFragment() {
        goToFragment(YoutubeFragment.newInstance(getListener()), R.id.fl)
    }

    private fun getListener(): YoutubeFragment.Listener {
        return object : YoutubeFragment.Listener {
            override fun onRefreshPoints() {
                homeViewModel.processProfileData()
            }
        }
    }
}