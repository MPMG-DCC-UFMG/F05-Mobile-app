package org.mpmg.mpapp.ui.fragments.collect.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_collect_main.*
import kotlinx.android.synthetic.main.fragment_public_work_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.domain.models.Photo
import org.mpmg.mpapp.ui.fragments.collect.adapters.PhotoListAdapter
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.PhotoViewModel

class CollectMainFragment : Fragment(), PhotoListAdapter.PhotoListAdapterListener {

    private val TAG = CollectMainFragment::class.java.name

    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val photoViewModel: PhotoViewModel by sharedViewModel()

    private var navigationController: NavController? = null
    private lateinit var photoListAdapter: PhotoListAdapter


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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed = {
            collectViewModel.updateCollect()
            navigateTo(R.id.action_collectMainFragment_pop)
        }, enabled = true)

        setupListeners()
        setupRecyclerView()
        setupViewModels()
    }

    private fun setupViewModels() {
        collectViewModel.getPhotoList()
            .observe(viewLifecycleOwner, Observer { photoList ->
                photoList ?: return@Observer

                photoListAdapter.updatePhotoList(photoList.toList())
            })
    }

    private fun setupRecyclerView() {
        initPublicWorkAdapter()
        recyclerView_collectMainFragment_photoList.adapter = photoListAdapter
        recyclerView_collectMainFragment_photoList.layoutManager = LinearLayoutManager(activity)
    }

    private fun initPublicWorkAdapter() {
        photoListAdapter = PhotoListAdapter()
        photoListAdapter.setPhotoListAdapterListener(this)
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

    override fun onPhotoClicked(photo: Photo) {
        photoViewModel.setPhoto(photo)
        navigateTo(R.id.action_collectMainFragment_to_photoAddFragment)
    }


}