package org.mpmg.mpapp.ui.fragments.publicwork.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_public_work_add.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observeOnce
import org.mpmg.mpapp.databinding.FragmentPublicWorkAddBinding
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel
import org.mpmg.mpapp.ui.viewmodels.TypeWorkViewModel

class PublicWorkAddFragment : Fragment() {

    private val TAG = PublicWorkAddFragment::class.java.name

    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()
    private val typeWorkViewModel: TypeWorkViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPublicWorkAddBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_public_work_add, container, false)
        binding.publicWorkViewModel = publicWorkViewModel
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
                publicWorkViewModel.setCurrentTypeWork(typeWorkList.first())
            })
    }

    private fun setupListeners() {
        materialButton_addPublicWorkFragment_add.setOnClickListener {
            publicWorkViewModel.addPublicWork()
        }
    }
}