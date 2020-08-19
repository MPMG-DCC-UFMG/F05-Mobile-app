package org.mpmg.mpapp.ui.screens.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.screens.home.adapters.HomeOptionsAdapter
import org.mpmg.mpapp.ui.screens.home.models.HomeOptions
import org.mpmg.mpapp.ui.viewmodels.HomeViewModel
import org.mpmg.mpapp.ui.viewmodels.LoginViewModel
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel
import java.lang.Exception

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.name

    private lateinit var homeOptionsAdapter: HomeOptionsAdapter
    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private val homeViewModel: HomeViewModel by sharedViewModel()

    private lateinit var optionsList: List<HomeOptions>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initOptions()
        setupViewModel()
    }

    private fun initRecyclerView() {
        initAdapter()
        recyclerView_homeFragment_options.adapter = homeOptionsAdapter
        recyclerView_homeFragment_options.layoutManager = LinearLayoutManager(activity)
    }

    private fun initAdapter() {
        homeOptionsAdapter = HomeOptionsAdapter()
    }

    private fun initOptions() {
        optionsList = listOf(
            HomeOptions(R.drawable.ic_list, getString(R.string.home_option_list)) {
                navigateTo(R.id.action_homeFragment_to_baseFragment)
            },
            HomeOptions(R.drawable.ic_plus, getString(R.string.home_option_new)) {
                publicWorkViewModel.newCurrentPublicWorkAddress()
                navigateTo(R.id.action_homeFragment_to_publicWorkAddFragment)
            },
            HomeOptions(
                R.drawable.ic_send,
                getString(R.string.home_option_send)
            ) {
                navigateTo(R.id.action_homeFragment_to_uploadDataFragment)
            },
            HomeOptions(R.drawable.ic_exit, getString(R.string.home_option_exit)) {
                loginViewModel.logout()
                navigateTo(R.id.action_homeFragment_to_loginFragment)
            }
        )
        homeOptionsAdapter.setOptions(optionsList)
    }

    private fun setupViewModel() {
        homeViewModel.countToSendLive.observe(viewLifecycleOwner, Observer { dataToSend ->
            dataToSend ?: return@Observer
            try {
                optionsList[2].eventCount = dataToSend
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        })
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }
}