package org.mpmg.mpapp.ui.screens.surveywork.fragments.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentSurveyWorkBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.publicwork.fragments.list.PublicWorkListFragment
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.screens.surveywork.adapters.ItemSurveyListAdapter
import org.mpmg.mpapp.ui.screens.surveywork.models.ItemSurveyList


class SurveyWorkFragment : MVVMFragment<PublicWorkListViewModel, FragmentSurveyWorkBinding>() {

    private val session = getKoin().getScope(PublicWorkListFragment.sessionName)

    override val viewModel: PublicWorkListViewModel by session.inject()
    override val layout: Int = R.layout.fragment_survey_work

    private lateinit var surveyListAdapter: ItemSurveyListAdapter
    private val surveyList: MutableList<ItemSurveyList> = mutableListOf()


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

        val recyclerViewSurvey = binding.recyclerViewSurveyList
        recyclerViewSurvey.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewSurvey.setHasFixedSize(true)
        surveyListAdapter = ItemSurveyListAdapter(requireActivity(), surveyList, viewModel)
        recyclerViewSurvey.adapter = surveyListAdapter
    }

    override fun initObservers() {
        observe(viewModel.publicWorkMediatedList) { publicWorkList ->
            surveyListAdapter.updatePublicWorksList(publicWorkList)
        }
    }

}