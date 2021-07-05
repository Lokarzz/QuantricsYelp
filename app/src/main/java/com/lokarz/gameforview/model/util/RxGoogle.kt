package com.lokarz.gameforview.model.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.lokarz.gameforview.pojo.google.GoogleAccount
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter
import javax.inject.Inject


class RxGoogle @Inject constructor(private var appCompatActivity: AppCompatActivity) {

    private var singleEmitter: SingleEmitter<Boolean>? = null

    private var googleResultActivityResultContracts =
        appCompatActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            singleEmitter ?: return@registerForActivityResult
            val completedTask = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                completedTask.getResult(ApiException::class.java)
                singleEmitter?.onSuccess(true)
            } catch (e: ApiException) {
                singleEmitter?.onError(Throwable(e.message))
            }
        }


    fun login(): Single<Boolean> {
        return Single.create { source ->
            singleEmitter = source
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(appCompatActivity, gso)
            val signInIntent = googleSignInClient.signInIntent

            if (GoogleSignIn.getLastSignedInAccount(appCompatActivity) != null) {
                source.onSuccess(true)
            } else {
                googleResultActivityResultContracts.launch(signInIntent)
            }
        }
    }


    fun getData(): Single<GoogleAccount> {
        return Single.create { source ->
            val account = GoogleSignIn.getLastSignedInAccount(appCompatActivity)
            if (account != null) {
                val googleAccount = GoogleAccount()
                googleAccount.name = account.displayName
                googleAccount.photo = if (account.photoUrl != null) {
                    account.photoUrl.toString()
                } else {
                    null
                }
                googleAccount.email = account.email

                source.onSuccess(googleAccount)
            } else {
                source.onError(Throwable("noLoginAccount"))
            }
        }
    }


    fun logout(
    ): Single<Boolean> {
        return Single.create { source ->
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(appCompatActivity, gso)
            googleSignInClient.signOut().addOnCompleteListener(
                appCompatActivity
            ) {
                source.onSuccess(true)
            }
        }
    }
}