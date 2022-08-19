package org.mpmg.mpapp.ui.screens.surveywork.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.ActivityMainBinding
import org.mpmg.mpapp.databinding.FragmentSurveyWorkBinding
import org.mpmg.mpapp.ui.MainActivity
import org.mpmg.mpapp.ui.screens.surveywork.adapters.ItemSurveyListAdapter
import org.mpmg.mpapp.ui.screens.surveywork.fragments.BaseSurveyFragment
import org.mpmg.mpapp.ui.screens.surveywork.models.ItemSurveyList
import org.mpmg.mpapp.ui.screens.surveywork.models.fakeSurveys


class SurveyWorkFragment : Fragment() {

    private lateinit var binding: FragmentSurveyWorkBinding
    private lateinit var surveyListAdapter: ItemSurveyListAdapter
    private val surveyList: MutableList<ItemSurveyList> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_work, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSurveyWorkBinding.bind(view)
        surveyListAdapter = (activity as MainActivity).surveyListAdapter
        surveyListAdapter



        val recyclerViewSurvey = binding.recyclerViewSurveyList
        recyclerViewSurvey.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewSurvey.setHasFixedSize(true)
        surveyListAdapter = ItemSurveyListAdapter(requireContext(), surveyList)
        recyclerViewSurvey.adapter = surveyListAdapter



        itemsList()
    }

    private fun itemsList() {
        val item1 = ItemSurveyList(surveyNumber = 12555, surveyAddress = "rua eleonor casa b", surveyStatus = false, surveySync = true, surveyTitle = "Tipo de vistoria: Pavimentação")
        surveyList.add(item1)

        val item2 = ItemSurveyList(surveyNumber = 12254, surveyAddress = "Av. Amazonas, 5154 - Nova Suíça Belo Horizonte - 30421-056", surveyStatus = false, surveySync = true, surveyTitle = "Tipo de vistoria: Obra de arte")
        surveyList.add(item2)

        val item3 = ItemSurveyList(surveyNumber = 10245, surveyAddress = "Av. Amazonas, 5154 - Nova Suíça Belo Horizonte - 30421-056", surveyStatus = false, surveySync = true, surveyTitle = "Tipo de vistoria: Pavimentação")
        surveyList.add(item3)

        val item4 = ItemSurveyList(surveyNumber = 12879, surveyAddress = "rua eleonor casa b", surveyStatus = false, surveySync = true, surveyTitle = "Tipo de vistoria: Ponte")
        surveyList.add(item4)

        val item5 = ItemSurveyList(surveyNumber = 12698, surveyAddress = "Av. Amazonas, 5154 - Nova Suíça Belo Horizonte - 30421-056", surveyStatus = false, surveySync = true, surveyTitle = "Tipo de vistoria: Pavimentação")
        surveyList.add(item5)
    }
}