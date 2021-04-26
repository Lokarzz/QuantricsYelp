package com.lokarz.kotlinbaseapp.pedometer

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.lokarz.kotlinbaseapp.pedometer.pojo.PedometerData
import com.lokarz.kotlinbaseapp.pedometer.service.PedometerWorker
import com.lokarz.kotlinbaseapp.util.GsonUtil
import com.lokarz.kotlinbaseapp.util.PreferenceUtil
import com.tbruyelle.rxpermissions3.RxPermissions
import java.util.concurrent.TimeUnit

class Pedometer {
    companion object {
        private const val KEY: String = "PEDOMETER_KEY"
        private const val PEDOMETER_FILE: String = "PEDOMETER_FILE"

        fun start(appCompatActivity: AppCompatActivity) {
            val rxPermissions = RxPermissions(appCompatActivity)

            rxPermissions.request(Manifest.permission.ACTIVITY_RECOGNITION).subscribe { result ->
                run {
                    if (result) {
                        Log.w("Pedometer", "success permission")
                        val workRequest: PeriodicWorkRequest =
                            PeriodicWorkRequestBuilder<PedometerWorker>(1, TimeUnit.MINUTES).build()


                        WorkManager.getInstance(appCompatActivity).enqueueUniquePeriodicWork(KEY,
                            ExistingPeriodicWorkPolicy.KEEP, workRequest)
                    }
                }
            }

        }

        fun stop(appCompatActivity: AppCompatActivity){
            WorkManager.getInstance(appCompatActivity).cancelAllWorkByTag(KEY);
        }

        fun saveData(context: Context, pedometerData: PedometerData) {
            PreferenceUtil.saveData( context, KEY, GsonUtil.getGsonString(pedometerData), PEDOMETER_FILE)
        }

        fun clearData(context: Context) {
            PreferenceUtil.clearSavedData(context, PEDOMETER_FILE)
        }

        fun getData(context: Context): PedometerData {
            var pedometerData = GsonUtil.getGson(
                PreferenceUtil.readSavedData(context, KEY, PEDOMETER_FILE),
                PedometerData::class.java
            )
            if (pedometerData == null) {
                pedometerData = PedometerData();
            }
            if (pedometerData.stepsData == null) {
                pedometerData.stepsData = ArrayList()
            }
            return pedometerData;

        }

    }
}