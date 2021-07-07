package com.lokarz.gameforview.view.activity.login

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.SignInButton
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivityLoginBinding
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.base.BaseActivity
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityLoginBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )
        activityLoginBinding.lifecycleOwner = this

        initGoogleLogin()
    }


    fun initGoogleLogin() {
        val loginButton: SignInButton = findViewById(R.id.v_google_login)
        loginButton.setSize(SignInButton.SIZE_STANDARD)
        loginButton.setOnClickListener {
            loginViewModel.googleLogin().observe(this) {
                if (it == true) {
                    ActivityUtil.gotoScreen(this, HomeActivity::class.java)
                } else {
                    showToast(getString(R.string.google_login_failed))
                }
            }
        }
    }

}