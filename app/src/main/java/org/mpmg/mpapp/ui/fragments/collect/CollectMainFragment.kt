package org.mpmg.mpapp.ui.fragments.collect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.databinding.FragmentPublicWorkAddBinding
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel

class CollectMainFragment : Fragment() {

    private val TAG = CollectMainFragment::class.java.name

    private val collectViewModel: CollectViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCollectMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_collect_main, container, false)
        binding.collectViewModel = collectViewModel
        binding.lifecycleOwner = this
        return binding.root
    }
}