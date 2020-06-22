package org.mpmg.mpapp.ui.screens.publicwork.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_public_work_add.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observeOnce
import org.mpmg.mpapp.databinding.FragmentPublicWorkAddBinding
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel
import org.mpmg.mpapp.ui.viewmodels.TypeWorkViewModel

class PublicWorkAddFragment : Fragment() {

    private val TAG = PublicWorkAddFragment::class.java.name

    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()
    private val typeWorkViewModel: TypeWorkViewModel by sharedViewModel()

    private var navigationController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navigationController = activity?.findNavController(R.id.nav_host_fragment)

        val binding: FragmentPublicWorkAddBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_public_work_add, container, false)
        binding.publicWorkViewModel = publicWorkViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        initTypeWork()
    }

    private fun initTypeWork() {
        typeWorkViewModel.getTypeOfWorkList()
            .observeOnce(viewLifecycleOwner, Observer { typeWorkList ->
                publicWorkViewModel.setInitialTypeWork(typeWorkList[0])
            })
    }

    private fun setupListeners() {
        materialButton_addPublicWorkFragment_add.setOnClickListener {
            publicWorkViewModel.addPublicWork()
            navigateBack()
        }

        materialButton_addPublicWorkFragment_map.setOnClickListener {
            navigateToMap()
        }

        textView_addPublicWorkFragment_typeOfWork.setOnClickListener {
            launchTypeWorkDialog()
        }

        materialButton_addPublicWorkFragment_cancel.setOnClickListener {
            navigateBack()
        }
    }

    private fun launchTypeWorkDialog() {
        val typesWork = typeWorkViewModel.getTypeOfWorkList().value ?: return
        val optionsArray = typesWork.map { it.name }.toTypedArray()
        val builder = SelectorDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_type_photo_title))
            .withOptions(optionsArray.toList())
            .withSelectionMode(SelectorDialog.SelectionMode.SINGLE)
            .withSelectedOptionListener {
                publicWorkViewModel.setCurrentTypeWork(typesWork[it.first()])
            }
            .show()
    }

    private fun navigateBack() {
        navigationController?.navigate(R.id.action_publicWorkAddFragment_pop)
    }

    private fun navigateToMap() {
        navigationController?.navigate(R.id.action_publicWorkAddFragment_to_mapFragment)
    }


}