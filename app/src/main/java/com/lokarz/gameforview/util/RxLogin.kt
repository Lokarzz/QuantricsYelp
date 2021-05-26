package com.lokarz.gameforview.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import io.reactivex.rxjava3.core.Single


class RxLogin {

    interface OnResultListener<in T> {
        fun onResult(t: T)
        fun onError(message: String?)
    }

    companion object {

        fun googleLogin(
            appCompatActivity: AppCompatActivity,
        ): Single<Boolean> {
            return Single.create { source ->
                val googleServerClientId = ""
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(googleServerClientId)
                    .requestEmail()
                    .build()
                val googleSignInClient = GoogleSignIn.getClient(appCompatActivity, gso)
                val signInIntent = googleSignInClient.signInIntent

                val googleActivityResult =
                    appCompatActivity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                        val completedTask = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                        try {
                            completedTask.getResult(ApiException::class.java)
                            source.onSuccess(true)
                        } catch (e: ApiException) {
                            source.onSuccess(false)
                        }
                    }
                if (GoogleSignIn.getLastSignedInAccount(appCompatActivity) != null) {
                    googleSignInClient.signOut().addOnCompleteListener(
                        appCompatActivity
                    ) {
                        googleActivityResult.launch(signInIntent)
                    }
                } else {
                    googleActivityResult.launch(signInIntent)
                }
            }
        }
    }
}