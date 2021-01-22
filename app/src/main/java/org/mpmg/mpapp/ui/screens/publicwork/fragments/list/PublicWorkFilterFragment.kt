package org.mpmg.mpapp.ui.screens.publicwork.fragments.list

import android.os.Bundle
import org.koin.android.ext.android.getKoin
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentPublicWorkFilterBinding
import org.mpmg.mpapp.domain.database.models.TypeWork
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.shared.filters.SyncStatus.*

class PublicWorkFilterFragment :
    MVVMFragment<PublicWorkListViewModel, FragmentPublicWorkFilterBinding>() {

    private val TAG = PublicWorkFilterFragment::class.java.name

    private val session = getKoin().getScope(PublicWorkListFragment.sessionName)
    override val viewModel: PublicWorkListViewModel by session.inject()
    override val layout: Int = R.layout.fragment_public_work_filter

    lateinit var typeWorkList: List<TypeWork>

    override fun initBindings() {
        binding.publicWorkViewModel = viewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {
        startCheckboxesState()
    }

    override fun initObservers() {
        observe(viewModel.typeWorkList) {
            typeWorkList = it
            updateFilterTypeOfWorksText()
        }
    }

    override fun initListeners() {
        with(binding) {
            checkboxFilterFragmentComplete.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateSyncStatusFilter(COLLECTED, isChecked)
            }

            checkboxFilterFragmentSent.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateSyncStatusFilter(TO_SEND, isChecked)
            }

            textViewFilterFragmentTypeOfWork.setOnClickListener {
                launchTypeWorkDialog()
            }
        }
    }

    private fun startCheckboxesState() {
        with(binding) {
            checkboxFilterFragmentComplete.isChecked = viewModel.isSyncStatusChecked(COLLECTED)
            checkboxFilterFragmentSent.isChecked = viewModel.isSyncStatusChecked(TO_SEND)
        }
    }

    private fun updateFilterTypeOfWorksText() {
        val checkedOptions =
            viewModel.getFilteredTypeOfWorks(typeWorkList.map { it.flag }.toTypedArray())
        val names = mutableListOf<String>()
        typeWorkList.forEachIndexed { index, typeWork ->
            if (checkedOptions[index]) {
                names.add(typeWork.name)
            }
        }
        with(binding.textViewFilterFragmentTypeOfWork) {
            text = names.joinToString(",")
        }
    }

    private fun launchTypeWorkDialog() {
        val optionsArray = typeWorkList.map { it.name }.toTypedArray()
        val checkedOptions =
            viewModel.getFilteredTypeOfWorks(typeWorkList.map { it.flag }.toTypedArray())
                .withIndex().filter { it.value }.map { it.index }

        val builder = SelectorDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_type_work_title))
            .withOptions(optionsArray.toList())
            .withSelectionMode(SelectorDialog.SelectionMode.MULTIPLE)
//            .withSelectedOptionListener {
//                viewModel.setCurrentTypeWork(typesWork[it.first()])
//            }
            .withOnPositiveClickListener {
                val selected = it.toSet()
                viewModel.updateTypeWorkFilter(typeWorkList.filterIndexed { index, _ ->
                    selected.contains(index)
                }.map { typeWork -> typeWork.flag })
                updateFilterTypeOfWorksText()
            }
            .withOnNegativeClickListener {
                // ignore
            }
            .withSelectedOptions(checkedOptions)
            .show()
    }
}