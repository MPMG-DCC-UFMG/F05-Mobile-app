package org.mpmg.mpapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.mpmg.mpapp.R

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationController()
    }

    private fun setupNavigationController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }
}
