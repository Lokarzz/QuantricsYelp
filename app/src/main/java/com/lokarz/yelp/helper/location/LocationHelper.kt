package com.lokarz.yelp.helper.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.lokarz.yelp.helper.permission.PermissionHelper
import io.reactivex.rxjava3.core.Single

class LocationHelper(
    private val appCompatActivity: AppCompatActivity,
    private val permissionHelper: PermissionHelper
) {

    fun goToSettings(appCompatActivity: AppCompatActivity) {
        appCompatActivity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    fun requestPermission(): Single<Boolean> {
        return permissionHelper.request(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    private fun isPermissionGranted(): Boolean {
        return permissionHelper.isGranted(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): Single<Location> {
        return Single.create { source ->
            if (isPermissionGranted()) {
                val fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(appCompatActivity)

                val locationRequest = LocationRequest.create().apply {
                    interval = 1000
                    fastestInterval = 1000
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationresult: LocationResult) {
                            super.onLocationResult(locationresult)
                            fusedLocationProviderClient.removeLocationUpdates(this)


                            source.onSuccess(locationresult.lastLocation)

                        }
                    },
                    Looper.getMainLooper()
                )
            } else {
                source.onError(Throwable(PERMISSION_NOT_GRANTED))
            }

        }
    }

    companion object {
        const val PERMISSION_NOT_GRANTED = "permission_not_granted"
    }
}