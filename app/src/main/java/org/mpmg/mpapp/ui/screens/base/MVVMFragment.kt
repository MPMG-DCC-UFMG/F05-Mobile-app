package org.mpmg.mpapp.ui.screens.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.ui.MainActivityViewModel

abstract class MVVMFragment<T : MVVMViewModel, B : ViewDataBinding> : Fragment() {

    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    internal abstract val viewModel: T
    internal abstract val layout: Int

    internal lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, layout, container, false
        )
        binding.lifecycleOwner = this
        initBindings()

        return binding.root
    }

    private fun initBaseObservers() {
        observe(viewModel.snackMessage) {
            it?:return@observe
            mainActivityViewModel.launchSnackBar(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(savedInstanceState)
        initBaseObservers()
        initObservers()
        initListeners()
    }

    internal open fun initBindings() {}
    internal open fun initViews(savedInstanceState: Bundle?) {}
    internal open fun initObservers() {}
    internal open fun initListeners() {}
}