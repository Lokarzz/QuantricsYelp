package com.lokarz.yelp.view.activity.splash

import android.os.Bundle
import com.lokarz.yelp.databinding.ActivitySplashBinding
import com.lokarz.yelp.util.ActivityUtil
import com.lokarz.yelp.view.activity.home.HomeActivity
import com.lokarz.yelp.view.base.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

//    @Inject
//    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        setContentView(binding.root)



        Single.just(1)
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _ ->
                ActivityUtil.gotoScreen(this, HomeActivity::class.java)
            }
    }

}