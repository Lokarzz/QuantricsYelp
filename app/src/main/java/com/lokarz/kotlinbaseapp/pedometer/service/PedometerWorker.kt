package com.lokarz.kotlinbaseapp.pedometer.service

import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.lokarz.kotlinbaseapp.R
import com.lokarz.kotlinbaseapp.pedometer.Pedometer
import com.lokarz.kotlinbaseapp.pedometer.pojo.StepsData
import com.lokarz.kotlinbaseapp.util.DateUtil
import com.lokarz.kotlinbaseapp.util.ParseUtil
import java.text.SimpleDateFormat
import java.util.*

/*
Add this permission to your manifest
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
*/
class PedometerWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private val mContext = context;
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager

    override suspend fun doWork(): Result {
        Log.w("PedometerWorker", "doWork")


        setForeground(createForegroundInfo())

        val sensorManager = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)


        stepCounterSensor?.let {
            sensorManager.registerListener(
                getSensorEventListener(),
                it,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }

        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val id = "0"
        val title = mContext.getString(R.string.app_name)
        // This PendingIntent can be used to cancel the worker
//        val intent = WorkManager.getInstance(applicationContext)
//            .createCancelPendingIntent(getId())

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText("Tracking")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
//            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ForegroundInfo(0, notification, FOREGROUND_SERVICE_TYPE_NONE)
        } else {
            ForegroundInfo(0, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
    }

    private fun getTimeStamp(sensorTimeStamp: Long): Long? {
        val lastDeviceBootTimeInMillis =
            System.currentTimeMillis() - SystemClock.elapsedRealtime()
        val sensorEventTimeInMillis = sensorTimeStamp / 1000_000
        val actualSensorEventTimeInMillis =
            lastDeviceBootTimeInMillis + sensorEventTimeInMillis
        val displayDateStr =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                actualSensorEventTimeInMillis)

        return DateUtil.getDateToMillis(displayDateStr, "yyyy-MM-dd HH:mm");
    }

    private fun getSensorEventListener(): SensorEventListener {
        val pedometerData = Pedometer.getData(mContext)
        return object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent?) {
                sensorEvent ?: return
                Log.w("PedometerWorker", "onSensorChanged")

                val sensorStepValue = ParseUtil.getInt(sensorEvent?.values[0]);
                if (pedometerData.stepsOnLastTimeStamp == 0 || pedometerData.stepsOnLastTimeStamp > sensorStepValue) {
                    pedometerData.stepsOnLastTimeStamp = sensorStepValue
                }

                if (pedometerData.stepsOnLastTimeStamp == sensorStepValue) {
                    return
                }
                val steps = sensorStepValue - pedometerData.stepsOnLastTimeStamp
                val timeStamp = getTimeStamp(sensorEvent.timestamp)

                var stepData =
                    if (pedometerData.stepsData.isNullOrEmpty()) null else pedometerData.stepsData?.last();


                if (stepData == null || stepData.timeStamp != timeStamp) {
                    stepData = StepsData(timeStamp, steps);
                    pedometerData.stepsData?.add(stepData)
                } else {
                    stepData.steps += steps
                }

                pedometerData.stepsOnLastTimeStamp = sensorStepValue

                Pedometer.saveData(mContext, pedometerData)

            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // do nothing
            }

        }
    }

}