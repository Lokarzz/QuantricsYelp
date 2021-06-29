package com.lokarz.gameforview.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class RxPermission(var appCompatActivity: AppCompatActivity) {

    var singleEmitter: SingleEmitter<Boolean>? = null

    private val permissionResult =
        appCompatActivity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            var success = true
            for (entry in it) {
                success = entry.value
                if (!success) {
                    break
                }
            }
            singleEmitter?.onSuccess(success)
        }

    fun request(
        vararg permissions: String
    ): Single<Boolean> {
        return Single.create {
            singleEmitter = it
            permissionResult.launch(permissions)

        }
    }
}

