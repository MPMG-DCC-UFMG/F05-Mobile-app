package org.mpmg.mpapp.ui.fragments.collect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_collect_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.databinding.FragmentPublicWorkAddBinding
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.PhotoViewModel

class CollectMainFragment : Fragment() {

    private val TAG = CollectMainFragment::class.java.name

    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val photoViewModel: PhotoViewModel by sharedViewModel()

    private var navigationController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navigationController = activity?.findNavController(R.id.nav_host_fragment)

        val binding: FragmentCollectMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_collect_main, container, false)
        binding.collectViewModel = collectViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        materialButton_collectMainFragment_edit.setOnClickListener {
            navigateTo(R.id.action_collectMainFragment_to_publicWorkAddFragment2)
        }

        materialButton_collectMainFragment_addPhoto.setOnClickListener {
            photoViewModel.setPhoto(null)
            navigateTo(R.id.action_collectMainFragment_to_photoAddFragment)
        }

        materialButton_collectMainFragment_addComment.setOnClickListener {

        }
    }

    private fun navigateTo(actionId: Int) {
        navigationController?.navigate(actionId)
    }
}