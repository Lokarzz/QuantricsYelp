package com.lokarz.kotlinbaseapp.view.activites

import android.os.Bundle
import com.lokarz.kotlinbaseapp.R

class SplashActivity : BaseActivity() {

//    @Inject
//    lateinit var info: Info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        Log.w("asdf", info.name)
    }
}