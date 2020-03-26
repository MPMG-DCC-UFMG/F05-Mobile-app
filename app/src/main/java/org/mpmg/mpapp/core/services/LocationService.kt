package org.mpmg.mpapp.core.services

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.*

class LocationService : Service() {

    private val TAG = LocationService::class.java.simpleName

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mLocation: Location? = null
    private lateinit var mFusedLocationUpdateCallback: LocationCallback

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        super.onCreate()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initLocationCallback()
        setupFusedLocationClient()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            mFusedLocationClient.removeLocationUpdates(mFusedLocationUpdateCallback)
        } catch (e: Exception) {
            Log.e(TAG, "Error when trying to remove location callback")
        }
    }

    private fun initLocationCallback() {
        mFusedLocationUpdateCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                for (location in locationResult.locations) {
                    if (location != null) {
                        updateLocation(location)
                    }
                }
            }
        }
    }

    private fun updateLocation(location: Location) {
        mLocation = location
    }

    private fun setupFusedLocationClient() {
        val locationRequest = LocationRequest.create().apply {
            interval = 20 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            updateLocation(location)
        }
        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            mFusedLocationUpdateCallback,
            null
        )
    }
}