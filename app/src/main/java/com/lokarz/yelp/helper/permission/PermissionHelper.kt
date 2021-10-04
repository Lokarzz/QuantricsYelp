package com.lokarz.yelp.helper.permission

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleEmitter

class PermissionHelper(private val appCompatActivity: AppCompatActivity) {

    private var singleEmitter: SingleEmitter<Boolean>? = null

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
            if (!isGranted(*permissions)) {
                permissionResult.launch(permissions)
            } else {
                singleEmitter?.onSuccess(true)
            }

        }
    }

    fun isGranted(
        vararg permissions: String
    ): Boolean {
        var ret = true

        for (permission in permissions) {
            ret = ContextCompat.checkSelfPermission(
                appCompatActivity,
                permission
            ) == PackageManager.PERMISSION_GRANTED
            if (!ret) {
                break
            }
        }
        return ret
    }
}

