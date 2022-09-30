package org.mpmg.mpapp.ui.screens.surveywork.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.mpmg.mpapp.databinding.ItemInspectionListBinding
import org.mpmg.mpapp.databinding.ItemPublicSurveyListBinding
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.SurveyWorkRemote
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.screens.surveywork.models.ItemSurveyList

class ItemSurveyListAdapter(
    private val fragmentActivity: FragmentActivity,
    private val surveyList: MutableList<ItemSurveyList>,
    private val viewModel: PublicWorkListViewModel
) :
    RecyclerView.Adapter<ItemSurveyListAdapter.ItemSurveyListViewHolder>() {

    private lateinit var navigationController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSurveyListViewHolder {
        val listItem =
            ItemPublicSurveyListBinding.inflate(
                LayoutInflater.from(fragmentActivity),
                parent,
                false
            )
        return ItemSurveyListViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ItemSurveyListViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val survey = surveyList[position]
            viewModel.retrieveInspections(survey.publicWorkId)
                .catch {
                    Log.i("crhisn", it.message, it)
                }
                .collect {
                    fragmentActivity.runOnUiThread {
                        holder.bind(survey, it)
                    }
                }
        }

    }

    override fun getItemCount() = surveyList.size
    fun updatePublicWorksList(publicWorkList: List<PublicWorkAndAddress>) {
        surveyList.clear()
        surveyList.addAll(publicWorkList.map {
            ItemSurveyList(
                surveyTitle = it.publicWork.name,
                surveyAddress = it.address.street,
                surveyNumber = 0,
                surveyStatus = false,
                surveySync = false,
                publicWorkId = it.publicWork.id
            )
        })
    }

    inner class ItemSurveyListViewHolder(val binding: ItemPublicSurveyListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemSurveyList, list: List<SurveyWorkRemote>) {
            binding.itemSurveyListName.text = item.surveyTitle
            binding.textViewItemPublicSurveyListAddress.text = item.surveyAddress
            binding.textViewItemPublicSurveyListNumber.text = item.surveyNumber.toString()

            binding.inspectionsList.apply {
                layoutManager =
                    LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
                adapter = object : RecyclerView.Adapter<ItemInspectionViewHolder>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ) = ItemInspectionViewHolder(
                        ItemInspectionListBinding.inflate(
                            LayoutInflater.from(fragmentActivity),
                            parent,
                            false
                        )
                    )

                    override fun onBindViewHolder(holder: ItemInspectionViewHolder, position: Int) {
                        holder.bind(list[position])
                    }

                    override fun getItemCount() = list.size

                }
            }
        }
    }

    inner class ItemInspectionViewHolder(val binding: ItemInspectionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(survey: SurveyWorkRemote) {
            binding.inspectionName.text = "${survey.name} ${survey.description}"
        }
    }
}