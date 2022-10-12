package org.mpmg.mpapp.ui.screens.inspection.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.mpmg.mpapp.databinding.ItemInspectionListBinding
import org.mpmg.mpapp.databinding.ItemPublicInspectionListBinding
import org.mpmg.mpapp.domain.database.models.relations.PublicWorkAndAddress
import org.mpmg.mpapp.domain.network.models.SurveyWorkRemote
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.screens.inspection.models.ItemInspectionList

class ItemInspectionListAdapter(
    private val fragmentActivity: FragmentActivity,
    private val inspectionList: MutableList<ItemInspectionList>,
    private val viewModel: PublicWorkListViewModel
) :
    RecyclerView.Adapter<ItemInspectionListAdapter.ItemSurveyListViewHolder>() {

    private lateinit var navigationController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSurveyListViewHolder {
        val listItem =
            ItemPublicInspectionListBinding.inflate(
                LayoutInflater.from(fragmentActivity),
                parent,
                false
            )
        return ItemSurveyListViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ItemSurveyListViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val survey = inspectionList[position]
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

    override fun getItemCount() = inspectionList.size
    fun updatePublicWorksList(publicWorkList: List<PublicWorkAndAddress>) {
        inspectionList.clear()
        inspectionList.addAll(publicWorkList.map {
            ItemInspectionList(
                inspectionTitle = it.publicWork.name,
                inspectionAddress = it.address.street,
                inspectionNumber = 0,
                inspectionStatus = false,
                inspectionSync = false,
                publicWorkId = it.publicWork.id
            )
        })
    }

    inner class ItemSurveyListViewHolder(val binding: ItemPublicInspectionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemInspectionList, list: List<SurveyWorkRemote>) {
            binding.itemInspectionListName.text = item.inspectionTitle
            binding.textViewItemPublicInspectionListAddress.text = item.inspectionAddress
            binding.textViewItemPublicInspectionListNumber.text = item.inspectionNumber.toString()

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