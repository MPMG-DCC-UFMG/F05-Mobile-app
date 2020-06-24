package org.mpmg.mpapp.ui.screens.publicwork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_public_work_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentPublicWorkListBinding
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.ui.screens.publicwork.adapters.PublicWorkListAdapter
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel

class PublicWorkListFragment : Fragment(), PublicWorkListAdapter.PublicWorkListAdapterListener {

    private val TAG = PublicWorkListFragment::class.java.name

    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()
    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val locationViewModel: LocationViewModel by sharedViewModel()

    private lateinit var publicWorkListAdapter: PublicWorkListAdapter

    private var navigationController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navigationController = activity?.findNavController(R.id.nav_host_fragment)

        val binding: FragmentPublicWorkListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_public_work_list, container, false)
        binding.publicWorkViewModel = publicWorkViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModels()
    }

    private fun setupViewModels() {
        publicWorkViewModel.getPublicWorkList()
            .observe(viewLifecycleOwner, Observer { publicWorkList ->
                publicWorkList ?: return@Observer

                publicWorkListAdapter.updatePublicWorksList(publicWorkList)
            })

        locationViewModel.getCurrentLocationLiveData().observe(viewLifecycleOwner, Observer {
            publicWorkViewModel.updateCurrentLocation(it)
        })
    }

    private fun setupRecyclerView() {
        initPublicWorkAdapter()
        recyclerView_publicWorkListFragment_worksList.adapter = publicWorkListAdapter
        recyclerView_publicWorkListFragment_worksList.layoutManager = LinearLayoutManager(activity)
    }

    private fun initPublicWorkAdapter() {
        publicWorkListAdapter = PublicWorkListAdapter(locationViewModel)
        publicWorkListAdapter.setPublicWorkListAdapterListener(this)
    }

    override fun onPublicWorkClicked(publicWork: PublicWorkAndAddress) {
        collectViewModel.setPublicWork(publicWork)
        navigationController?.navigate(R.id.action_baseFragment_to_collectMainFragment)
    }
}