package org.mpmg.mpapp.services

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.*

class LocationService : Service() {

    private val TAG = LocationService::class.java.simpleName

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mLocation: Location? = null
    private lateinit var mFusedLocationUpdateCallback: LocationCallback

    private var mListener: LocationServiceListener? = null

    private val mBinder: IBinder = LocalBinder()

    inner class LocalBinder : Binder() {
        internal val service: LocationService
            get() = this@LocationService
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initLocationCallback()
        setupFusedLocationClient()
    }

    override fun onDestroy() {
        super.onDestroy()

        mListener = null

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
        mListener?.onNewLocation(location)
    }

    private fun setupFusedLocationClient() {
        val locationRequest = LocationRequest.create().apply {
            interval = 20 * 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location ?: return@addOnSuccessListener

            updateLocation(location)
        }
        mFusedLocationClient.requestLocationUpdates(
            locationRequest,
            mFusedLocationUpdateCallback,
            null
        )
    }

    fun setLocationServiceListener(listener: LocationServiceListener) {
        mListener = listener
    }

    interface LocationServiceListener {

        fun onNewLocation(location: Location)
    }
}