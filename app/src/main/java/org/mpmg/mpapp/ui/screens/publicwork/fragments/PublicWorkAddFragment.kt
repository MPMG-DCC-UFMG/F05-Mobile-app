package org.mpmg.mpapp.ui.screens.publicwork.fragments

import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentPublicWorkAddBinding
import org.mpmg.mpapp.domain.database.models.City
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.AddPublicWorkViewModel
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel


class PublicWorkAddFragment : MVVMFragment<AddPublicWorkViewModel, FragmentPublicWorkAddBinding>() {

    private val TAG = PublicWorkAddFragment::class.java.name

    override val viewModel: AddPublicWorkViewModel by viewModel()
    override val layout: Int = R.layout.fragment_public_work_add

    private val locationViewModel: LocationViewModel by sharedViewModel()

    private lateinit var navigationController: NavController

    override fun initBindings() {
        navigationController = requireActivity().findNavController(R.id.nav_host_fragment)
        binding.addPublicWorkViewModel = viewModel
    }

    override fun initObservers() {
        observe(viewModel.citiesList) {
            initCitiesTextView(it)
        }

        observe(viewModel.typeWorkList) {
            viewModel.setInitialTypeWork(it[0])
        }

        observe(locationViewModel.geocodingAddress) {
            it ?: return@observe
            viewModel.fromGeocoding(it)
        }

        observe(locationViewModel.selectedLocation) {
            it ?: return@observe
            viewModel.updateCurrPublicWorkLocation(it)
        }
    }

    override fun initListeners() {
        with(binding) {
            materialButtonAddPublicWorkFragmentAdd.setOnClickListener {
                viewModel.addPublicWork()
                navigateBack()
            }

            materialButtonAddPublicWorkFragmentMap.setOnClickListener {
                navigateToMap()
            }

            textViewAddPublicWorkFragmentTypeOfWork.setOnClickListener {
                launchTypeWorkDialog()
            }

            materialButtonAddPublicWorkFragmentCancel.setOnClickListener {
                navigateBack()
            }
        }
    }

    private fun initCitiesTextView(citiesList: List<City>) {
        val cityAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            citiesList.map { it.name }
        )
        with(binding.editTextAddPublicWorkFragmentCity) {
            setAdapter(cityAdapter)
            threshold = 1
        }
    }

    private fun launchTypeWorkDialog() {
        val typesWork = viewModel.typeWorkList.value ?: return
        val optionsArray = typesWork.map { it.name }.toTypedArray()
        val builder = SelectorDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_type_photo_title))
            .withOptions(optionsArray.toList())
            .withSelectionMode(SelectorDialog.SelectionMode.SINGLE)
            .withSelectedOptionListener {
                viewModel.setCurrentTypeWork(typesWork[it.first()])
            }
            .show()
    }

    private fun navigateBack() {
        navigationController.navigate(R.id.action_publicWorkAddFragment_pop)
    }

    private fun navigateToMap() {
        navigationController.navigate(R.id.action_publicWorkAddFragment_to_mapFragment)
    }

}