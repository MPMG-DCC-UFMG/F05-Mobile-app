package org.mpmg.mpapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import org.mpmg.mpapp.R
import java.util.jar.Manifest

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            continueTo(MainActivity::class.java)
        }, 500)
    }

    private fun continueTo(destinationActivity: Class<*>) {
        startActivity(Intent(this@SplashActivity, destinationActivity))
        finish()
    }
}