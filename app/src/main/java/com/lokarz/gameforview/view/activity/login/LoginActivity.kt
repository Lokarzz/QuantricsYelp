package com.lokarz.gameforview.view.activity.login

import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivityLoginBinding
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.util.RxLogin
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.base.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityLoginBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )
        activityLoginBinding.lifecycleOwner = this

        init()
        initGoogleLogin()
    }

    private fun init() {
    }

    fun initGoogleLogin() {
        val loginButton: AppCompatButton = findViewById(R.id.google_sign_in)
        loginButton.setOnClickListener {
            RxLogin.googleLogin(this).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { success ->
                    if (success) {
                        ActivityUtil.goToScreen(this, HomeActivity::class.java)
                    } else {
                        showToast("Google Login Failed")
                    }
                }
        }
    }

}