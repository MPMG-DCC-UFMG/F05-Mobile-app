package org.mpmg.mpapp.ui.screens.inspection.fragments.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentInspectionWorkBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.publicwork.fragments.list.PublicWorkListFragment
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.screens.inspection.adapters.ItemInspectionListAdapter
import org.mpmg.mpapp.ui.screens.inspection.models.ItemInspectionList


class InspectionWorkFragment : MVVMFragment<PublicWorkListViewModel, FragmentInspectionWorkBinding>() {

    private val session = getKoin().getScope(PublicWorkListFragment.sessionName)

    override val viewModel: PublicWorkListViewModel by session.inject()
    override val layout: Int = R.layout.fragment_inspection_work

    private lateinit var inspectionListAdapter: ItemInspectionListAdapter
    private val inspectionList: MutableList<ItemInspectionList> = mutableListOf()


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        val recyclerViewSurvey = binding.recyclerViewInspectionList
        recyclerViewSurvey.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewSurvey.setHasFixedSize(true)
        inspectionListAdapter = ItemInspectionListAdapter(requireActivity(), inspectionList, viewModel)
        recyclerViewSurvey.adapter = inspectionListAdapter
    }

    override fun initObservers() {
        observe(viewModel.publicWorkMediatedList) { publicWorkList ->
            inspectionListAdapter.updatePublicWorksList(publicWorkList)
        }
    }

}