package org.mpmg.mpapp.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_base.*
import org.mpmg.mpapp.R

class BaseFragment : Fragment() {

    private val TAG = BaseFragment::class.java.name

    lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigationController()
    }

    private fun setNavigationController(){
        activity?.let {
            navController = it.findNavController(R.id.navHost_baseFragment)
            bottomNavigationView_baseFragment.setupWithNavController(navController)
        }
    }
}