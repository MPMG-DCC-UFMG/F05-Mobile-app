package org.mpmg.mpapp.ui.screens.publicwork.fragments.list

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentPublicWorkListBinding
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.publicwork.adapters.PublicWorkListAdapter
import org.mpmg.mpapp.ui.screens.publicwork.fragments.PublicWorkBaseFragmentDirections
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class PublicWorkListFragment :
    MVVMFragment<PublicWorkListViewModel, FragmentPublicWorkListBinding>(),
    PublicWorkListAdapter.PublicWorkListAdapterListener {

    companion object {
        val sessionName = "PublicWorkListSession"
        val TAG = PublicWorkListFragment::class.java.name
    }

    private val session = getKoin().getScope(sessionName)

    override val viewModel: PublicWorkListViewModel by session.inject()
    override val layout: Int = R.layout.fragment_public_work_list

    private val locationViewModel: LocationViewModel by sharedViewModel()

    private lateinit var publicWorkListAdapter: PublicWorkListAdapter

    private lateinit var navigationController: NavController

    override fun initBindings() {
        navigationController = requireActivity().findNavController(R.id.nav_host_fragment)
        binding.publicWorkViewModel = viewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setupRecyclerView()
    }

    override fun initObservers() {
        observe(viewModel.publicWorkMediatedList) { publicWorkList ->
            publicWorkListAdapter.updatePublicWorksList(publicWorkList)
        }

        observe(locationViewModel.getCurrentLocationLiveData()) {
            viewModel.updateCurrentLocation(it)
        }
    }

    private fun setupRecyclerView() {
        initPublicWorkAdapter()
        with(binding.recyclerViewPublicWorkListFragmentWorksList) {
            adapter = publicWorkListAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initPublicWorkAdapter() {
        publicWorkListAdapter = PublicWorkListAdapter(locationViewModel)
        publicWorkListAdapter.setPublicWorkListAdapterListener(this)
    }

    override fun onPublicWorkClicked(publicWork: PublicWorkAndAddress) {
        val action =
            PublicWorkBaseFragmentDirections.actionBaseFragmentToCollectMainFragment(publicWork.publicWork.id)
        navigationController.navigate(action)
    }
}