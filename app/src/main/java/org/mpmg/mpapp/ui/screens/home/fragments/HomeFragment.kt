package org.mpmg.mpapp.ui.screens.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.screens.home.adapters.HomeOptionsAdapter
import org.mpmg.mpapp.ui.screens.home.models.HomeOptions
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.name

    private lateinit var homeOptionsAdapter: HomeOptionsAdapter
    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()

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
        homeOptionsAdapter.setOptions(
            listOf(
                HomeOptions(R.drawable.ic_list, getString(R.string.home_option_list)) {
                    navigateTo(R.id.action_homeFragment_to_baseFragment)
                },
                HomeOptions(R.drawable.ic_plus, getString(R.string.home_option_new)) {
                    publicWorkViewModel.newCurrentPublicWorkAddress()
                    navigateTo(R.id.action_homeFragment_to_publicWorkAddFragment)
                },
                HomeOptions(R.drawable.ic_send, getString(R.string.home_option_send)) {
                    navigateTo(R.id.action_homeFragment_to_uploadDataFragment)
                },
                HomeOptions(R.drawable.ic_help, getString(R.string.home_option_help)) {

                }
            )
        )
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(actionId)
    }
}