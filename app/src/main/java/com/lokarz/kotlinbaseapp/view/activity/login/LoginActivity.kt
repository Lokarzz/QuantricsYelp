package com.lokarz.kotlinbaseapp.view.activity.login

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.databinding.ActivityLoginBinding
import com.lokarz.kotlinbaseapp.util.ActivityUtil
import com.lokarz.kotlinbaseapp.util.RxLogin
import com.lokarz.kotlinbaseapp.view.activity.home.HomeActivity
import com.lokarz.kotlinbaseapp.view.base.BaseActivity
import com.lokarz.kotlinbaseapp.view.fragment.youtube.YoutubeFragment
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