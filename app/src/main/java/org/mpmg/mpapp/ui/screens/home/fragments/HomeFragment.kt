package org.mpmg.mpapp.ui.screens.home.fragments

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentHomeBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.home.adapters.HomeOptionsAdapter
import org.mpmg.mpapp.ui.screens.home.models.HomeOptions
import org.mpmg.mpapp.ui.screens.home.viewmodels.HomeViewModel

class HomeFragment : MVVMFragment<HomeViewModel, FragmentHomeBinding>() {

    private val TAG = HomeFragment::class.java.name

    override val viewModel: HomeViewModel by viewModel()
    override val layout: Int = R.layout.fragment_home

    private lateinit var homeOptionsAdapter: HomeOptionsAdapter
    private lateinit var optionsList: List<HomeOptions>

    override fun initViews(savedInstanceState: Bundle?) {
        initRecyclerView()
        initOptions()
    }

    override fun initObservers() {
        observe(viewModel.countToSendLive) { dataToSend ->
            try {
                optionsList[2].eventCount = dataToSend
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    private fun initRecyclerView() {
        initAdapter()
        with(binding.recyclerViewHomeFragmentOptions) {
            adapter = homeOptionsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initAdapter() {
        homeOptionsAdapter = HomeOptionsAdapter()
    }

    private fun initOptions() {
        optionsList = listOf(
            HomeOptions(R.drawable.ic_list, getString(R.string.home_option_survey)) {
                navigateTo(R.id.action_homeFragment_to_baseFragment)
            },
//            HomeOptions(R.drawable.ic_survey, getString(R.string.home_option_survey)) {
//                navigateTo(R.id.action_homeFragment_to_baseSurveyFragment)
//            },
            HomeOptions(R.drawable.ic_plus, getString(R.string.home_option_new)) {
                navigateTo(R.id.action_homeFragment_to_publicWorkAddFragment)
            },
            HomeOptions(R.drawable.ic_send, getString(R.string.home_option_send)) {
                navigateTo(R.id.action_homeFragment_to_uploadDataFragment)
            },
            HomeOptions(R.drawable.ic_exit, getString(R.string.home_option_exit)) {
                viewModel.logout()
                navigateTo(R.id.action_homeFragment_to_loginFragment)
            }
        )
        homeOptionsAdapter.setOptions(optionsList)
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }
}