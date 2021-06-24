package com.lokarz.gameforview.view.activity.login

import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.SignInButton
import com.lokarz.gameforview.R
import com.lokarz.gameforview.databinding.ActivityLoginBinding
import com.lokarz.gameforview.util.ActivityUtil
import com.lokarz.gameforview.util.GsonUtil
import com.lokarz.gameforview.util.PreferenceUtil
import com.lokarz.gameforview.util.RxGoogle
import com.lokarz.gameforview.view.activity.home.HomeActivity
import com.lokarz.gameforview.view.base.BaseActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var rxGoogle: RxGoogle

    @Inject
    lateinit var preferenceUtil: PreferenceUtil

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
        loginButton.setSize(SignInButton.SIZE_STANDARD);
        loginButton.setOnClickListener {
            rxGoogle.login().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { success ->
                    if (success) {
                        storeData()
                        ActivityUtil.gotoScreen(this, HomeActivity::class.java)
                    } else {
                        showToast(getString(R.string.google_login_failed))
                    }
                }
        }
    }

    private fun storeData() {
        rxGoogle.getData().subscribe { googleAccount ->
            preferenceUtil.saveData(
                googleAccount::class.simpleName,
                GsonUtil.getGsonString(googleAccount)
            )
        }
    }

}