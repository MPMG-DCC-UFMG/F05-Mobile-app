package org.mpmg.mpapp.ui.screens.collect.fragments

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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.ui.MainActivity
import org.mpmg.mpapp.ui.dialogs.CommentsBottomSheetDialog
import org.mpmg.mpapp.ui.screens.collect.adapters.PhotoListAdapter
import org.mpmg.mpapp.ui.viewmodels.CollectViewModel
import org.mpmg.mpapp.ui.viewmodels.PhotoViewModel
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel
import org.mpmg.mpapp.ui.viewmodels.TypeWorkViewModel

class CollectMainFragment : Fragment(), PhotoListAdapter.PhotoListAdapterListener {

    private val TAG = CollectMainFragment::class.java.name

    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val photoViewModel: PhotoViewModel by sharedViewModel()
    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()
    private val typeWorkViewModel: TypeWorkViewModel by sharedViewModel()

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
            navigateBack()
        }, enabled = true)

        setupListeners()
        setupRecyclerView()
        setupViewModels()
    }

    private fun setupViewModels() {
        collectViewModel.getPhotoList()
            .observe(viewLifecycleOwner, Observer { photoMap ->
                photoMap ?: return@Observer

                photoListAdapter.updatePhotoList(photoMap.values.toList())
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
            setSelectPublicWorkToEdit()
            navigateTo(R.id.action_collectMainFragment_to_publicWorkAddFragment)
        }

        materialButton_collectMainFragment_addPhoto.setOnClickListener {
            photoViewModel.setPhoto(null)
            navigateTo(R.id.action_collectMainFragment_to_photoAddFragment)
        }

        materialButton_collectMainFragment_addComment.setOnClickListener {
            launchBottomSheetComment()
        }
    }

    private fun setSelectPublicWorkToEdit() {
        collectViewModel.getPublicWork().value?.let {
            val typeWork = typeWorkViewModel.getTypeOfWorkFromFlag(it.publicWork.typeWorkFlag)
            publicWorkViewModel.setCurrentPublicWorkAddress(it)
            publicWorkViewModel.setCurrentTypeWork(typeWork)
        }
    }

    private fun launchBottomSheetComment() {
        val bottomSheetFragment = CommentsBottomSheetDialog(collectViewModel)
        bottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            bottomSheetFragment.tag
        )
    }

    private fun navigateTo(actionId: Int) {
        navigationController?.navigate(actionId)
    }

    private fun navigateBack() {
        val parentActivity = this.requireActivity()
        if (parentActivity is MainActivity) {
            parentActivity.launchSnackbar(getString(R.string.message_collect_updated))
        }
        collectViewModel.updateCollect()
        navigateTo(R.id.action_collectMainFragment_to_baseFragment)
    }

    override fun onPhotoClicked(photo: Photo) {
        photoViewModel.setPhoto(photo)
        navigateTo(R.id.action_collectMainFragment_to_photoAddFragment)
    }
}