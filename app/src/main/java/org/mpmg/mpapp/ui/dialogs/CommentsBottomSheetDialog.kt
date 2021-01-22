package org.mpmg.mpapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.BottomSheetCommentBinding
import org.mpmg.mpapp.ui.screens.collect.viewmodels.CollectFragmentViewModel

class CommentsBottomSheetDialog(private val collectViewModel: CollectFragmentViewModel) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: BottomSheetCommentBinding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_comment, container, false)
        binding.lifecycleOwner = this
        binding.collectViewModel = collectViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
    }

    private fun setupViewModels() {
        collectViewModel.comment.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            collectViewModel.updateCommentToCollect(it)
        })
    }
}