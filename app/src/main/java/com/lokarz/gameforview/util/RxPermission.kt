package com.lokarz.gameforview.util

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Single

class RxPermission {

    companion object {

        fun request(
            appCompatActivity: AppCompatActivity, vararg permissions: String
        ): Single<Boolean> {
            return Single.create { source ->
                val permissionResult =
                    appCompatActivity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                        var success = true
                        for (entry in it) {
                            success = entry.value
                            if (!success) {
                                break
                            }
                        }
                        source.onSuccess(success)
                    }
                permissionResult.launch(permissions)

            }
        }
    }
}