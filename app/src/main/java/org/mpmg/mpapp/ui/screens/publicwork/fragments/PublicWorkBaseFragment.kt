package org.mpmg.mpapp.ui.screens.publicwork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentBaseBinding
import org.mpmg.mpapp.ui.screens.publicwork.fragments.list.PublicWorkListFragment

class PublicWorkBaseFragment : Fragment() {

    private val TAG = PublicWorkBaseFragment::class.java.name

    lateinit var navController: NavController

    private lateinit var binding: FragmentBaseBinding

    private val session = getKoin().getOrCreateScope(
        PublicWorkListFragment.sessionName, named(
            PublicWorkListFragment.TAG
        )
    )

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigationController()
    }

    private fun setNavigationController() {
        activity?.let {
            navController = it.findNavController(R.id.navHost_baseFragment)
            with(binding.bottomNavigationViewBaseFragment) {
                setupWithNavController(navController)

                addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                    if (selectedItemId == R.id.base_graph) {
                        it.onBackPressed()
                    }
                }
            }

        }
    }
}