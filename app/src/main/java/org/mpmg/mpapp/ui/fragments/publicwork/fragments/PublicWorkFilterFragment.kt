package org.mpmg.mpapp.ui.fragments.publicwork.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_public_work_filter.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.shared.filters.SyncStatus
import org.mpmg.mpapp.ui.shared.filters.SyncStatus.*
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel
import org.mpmg.mpapp.ui.viewmodels.TypeWorkViewModel

class PublicWorkFilterFragment : Fragment() {

    private val TAG = PublicWorkFilterFragment::class.java.name

    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()
    private val typeWorkViewModel: TypeWorkViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_public_work_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCheckboxesState()
        setupListeners()
        updateFilterTypeOfWorksText()
    }

    private fun startCheckboxesState() {
        checkbox_filterFragment_complete.isChecked =
            publicWorkViewModel.isSyncStatusChecked(COLLECTED)
        checkbox_filterFragment_sent.isChecked = publicWorkViewModel.isSyncStatusChecked(SENT)
    }

    private fun setupListeners() {
        checkbox_filterFragment_complete.setOnCheckedChangeListener { _, isChecked ->
            publicWorkViewModel.updateSyncStatusFilter(COLLECTED, isChecked)
        }

        checkbox_filterFragment_sent.setOnCheckedChangeListener { _, isChecked ->
            publicWorkViewModel.updateSyncStatusFilter(SENT, isChecked)
        }

        textView_filterFragment_typeOfWork.setOnClickListener {
            launchTypeWorkDialog()
        }
    }

    fun updateFilterTypeOfWorksText() {
        val typesWork = typeWorkViewModel.getTypeOfWorkList().value ?: return
        val checkedOptions =
            publicWorkViewModel.getFilteredTypeOfWorks(typesWork.map { it.flag }.toTypedArray())
        val names = mutableListOf<String>()
        typesWork.forEachIndexed { index, typeWork ->
            if (checkedOptions[index]) {
                names.add(typeWork.name)
            }
        }

        textView_filterFragment_typeOfWork.text = names.joinToString(",")
    }

    private fun launchTypeWorkDialog() {
        activity?.let {
            val typesWork = typeWorkViewModel.getTypeOfWorkList().value ?: return@let
            val optionsArray = typesWork.map { it.name }.toTypedArray()
            val checkedOptions =
                publicWorkViewModel.getFilteredTypeOfWorks(typesWork.map { it.flag }.toTypedArray())
            val builder =
                AlertDialog.Builder(it).setTitle(getString(R.string.dialog_type_work_filter_title))
                    .setMultiChoiceItems(optionsArray, checkedOptions) { dialog, which, isChecked ->
                        val checked = typesWork.get(which)
                        publicWorkViewModel.updateTypeWorkFilter(checked.flag, isChecked)
                        updateFilterTypeOfWorksText()
                    }

            builder.create().show()
        }
    }
}