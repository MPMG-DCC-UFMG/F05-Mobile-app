package org.mpmg.mpapp.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.BottomSheetCommentBinding
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel

class CommentsBottomSheetDialog(private val collectViewModel: CollectViewModel) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: BottomSheetCommentBinding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_comment, container, false)
        binding.lifecycleOwner = this
        binding.collectViewModel = collectViewModel
        return binding.root
    }
}