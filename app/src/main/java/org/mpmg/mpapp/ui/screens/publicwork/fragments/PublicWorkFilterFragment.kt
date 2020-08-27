package org.mpmg.mpapp.ui.screens.publicwork.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_public_work_filter.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentPublicWorkFilterBinding
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
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
        val binding: FragmentPublicWorkFilterBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_public_work_filter,
                container,
                false
            )
        binding.publicWorkViewModel = publicWorkViewModel
        binding.lifecycleOwner = this
        return binding.root
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
        checkbox_filterFragment_sent.isChecked = publicWorkViewModel.isSyncStatusChecked(TO_SEND)
    }

    private fun setupListeners() {
        checkbox_filterFragment_complete.setOnCheckedChangeListener { _, isChecked ->
            publicWorkViewModel.updateSyncStatusFilter(COLLECTED, isChecked)
        }

        checkbox_filterFragment_sent.setOnCheckedChangeListener { _, isChecked ->
            publicWorkViewModel.updateSyncStatusFilter(TO_SEND, isChecked)
        }

        textView_filterFragment_typeOfWork.setOnClickListener {
            launchTypeWorkDialog()
        }
    }

    private fun updateFilterTypeOfWorksText() {
        val typesWork = typeWorkViewModel.getTypeOfWorkList()
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
        val typesWork = typeWorkViewModel.getTypeOfWorkList()
        val optionsArray = typesWork.map { it.name }.toTypedArray()
        val checkedOptions =
            publicWorkViewModel.getFilteredTypeOfWorks(typesWork.map { it.flag }.toTypedArray())
                .withIndex().filter { it.value }.map { it.index }

        val builder = SelectorDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_type_photo_title))
            .withOptions(optionsArray.toList())
            .withSelectionMode(SelectorDialog.SelectionMode.MULTIPLE)
            .withSelectedOptionListener {
                publicWorkViewModel.setCurrentTypeWork(typesWork[it.first()])
            }
            .withOnPositiveClickListener {
                val selected = it.toSet()
                publicWorkViewModel.updateTypeWorkFilter(typesWork.filterIndexed { index, _ ->
                    selected.contains(
                        index
                    )
                }.map { typeWork -> typeWork.flag })
                updateFilterTypeOfWorksText()
            }
            .withSelectedOptions(checkedOptions)
            .show()
    }
}