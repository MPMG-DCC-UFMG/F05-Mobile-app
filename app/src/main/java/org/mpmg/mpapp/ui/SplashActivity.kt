package org.mpmg.mpapp.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.mpmg.mpapp.R


class SplashActivity : Activity() {

    private val PERMISSIONS_REQUEST = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val notGrantedPermissions = listPermissionsNotGranted()
        if (notGrantedPermissions.isEmpty()) {
            Handler().postDelayed({
                continueTo(MainActivity::class.java)
            }, 500)
        } else {
            requestNeededPermissions(notGrantedPermissions)
        }
    }

    private fun requestNeededPermissions(notGrantedPermissions: Array<String>) {
        ActivityCompat.requestPermissions(this, notGrantedPermissions, PERMISSIONS_REQUEST)
    }

    private fun checkPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun listPermissionsNotGranted(): Array<String> {
        val info: PackageInfo = packageManager.getPackageInfo(
            applicationContext.getPackageName(),
            PackageManager.GET_PERMISSIONS
        )
        val permissions = info.requestedPermissions
        val askPermissions = mutableListOf<String>()

        for (permission in permissions) {
            if (!checkPermissionGranted(permission)) {
                askPermissions.add(permission)
            }
        }

        return askPermissions.toTypedArray()
    }

    private fun continueTo(destinationActivity: Class<*>) {
        startActivity(Intent(this@SplashActivity, destinationActivity))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                continueTo(MainActivity::class.java)
            }
            else -> {
                //ignore
            }
        }
    }
}