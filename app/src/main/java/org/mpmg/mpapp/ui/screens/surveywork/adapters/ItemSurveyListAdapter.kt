package org.mpmg.mpapp.ui.screens.surveywork.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.ItemPublicSurveyListBinding
import org.mpmg.mpapp.ui.screens.publicwork.fragments.PublicWorkBaseFragment
import org.mpmg.mpapp.ui.screens.publicwork.fragments.PublicWorkBaseFragmentDirections
import org.mpmg.mpapp.ui.screens.surveywork.models.ItemSurveyList

class ItemSurveyListAdapter(private val context: Context, private val surveyList: MutableList<ItemSurveyList>):
    RecyclerView.Adapter<ItemSurveyListAdapter.ItemSurveyListViewHolder>() {

    private lateinit var navigationController: NavController

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSurveyListViewHolder {
        val listItem = ItemPublicSurveyListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ItemSurveyListViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ItemSurveyListViewHolder, position: Int) {
        holder.txtSurveyTitle.text = surveyList[position].surveyTitle
        holder.txtSurveyAddress.text = surveyList[position].surveyAddress
        holder.txtSurveyNumber.text = surveyList[position].surveyNumber.toString()
    }

    override fun getItemCount() = surveyList.size

    inner class ItemSurveyListViewHolder(binding: ItemPublicSurveyListBinding): RecyclerView.ViewHolder(binding.root) {

        val txtSurveyTitle = binding.itemSurveyListName
        val txtSurveyAddress = binding.textViewItemPublicSurveyListAddress
        val txtSurveyNumber = binding.textViewItemPublicSurveyListNumber

        /*val root = binding.root.setOnClickListener {
            Toast.makeText(context, "envio ", Toast.LENGTH_LONG).show()

            navigationController = Navigation.findNavController(this, R.id.nav_host_fragment)

        }*/

    }
}