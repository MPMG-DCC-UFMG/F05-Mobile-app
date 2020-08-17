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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observeOnce
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.ui.MainActivity
import org.mpmg.mpapp.ui.dialogs.CommentsBottomSheetDialog
import org.mpmg.mpapp.ui.dialogs.SelectorDialog
import org.mpmg.mpapp.ui.screens.collect.adapters.PhotoListAdapter
import org.mpmg.mpapp.ui.screens.collect.viewmodels.CollectFragmentViewModel
import org.mpmg.mpapp.ui.shared.animation.AnimationHelper
import org.mpmg.mpapp.ui.viewmodels.*


class CollectMainFragment : Fragment(), PhotoListAdapter.PhotoListAdapterListener {

    private val TAG = CollectMainFragment::class.java.name

    private val typeWorkViewModel: TypeWorkViewModel by sharedViewModel()
    private val collectViewModel: CollectViewModel by sharedViewModel()
    private val photoViewModel: PhotoViewModel by sharedViewModel()
    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()

    private val workStatusViewModel: WorkStatusViewModel by viewModel()
    private val collectFragmentViewModel: CollectFragmentViewModel by viewModel()

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
        binding.collectFragmentViewModel = collectFragmentViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed = {
            launchWorkStatusDialog()
        }, enabled = true)

        setupListeners()
        setupRecyclerView()
        setupViewModels()
        setupFABs()
    }

    private fun setupFABs() {
        collectFragmentViewModel.initMiniFABs(floatingActionButton_collectMainFragment_addComment)
        collectFragmentViewModel.initMiniFABs(floatingActionButton_collectMainFragment_addPhoto)
    }

    private fun setupViewModels() {
        collectViewModel.getPhotoList().observe(viewLifecycleOwner, Observer { photoMap ->
            photoMap ?: return@Observer

            photoListAdapter.updatePhotoList(photoMap.values.toList())
        })

        collectFragmentViewModel.rotated.observe(viewLifecycleOwner, Observer {
            if (it) {
                AnimationHelper.showView(floatingActionButton_collectMainFragment_addPhoto)
                AnimationHelper.showView(floatingActionButton_collectMainFragment_addComment)
            } else {
                AnimationHelper.hideView(floatingActionButton_collectMainFragment_addPhoto)
                AnimationHelper.hideView(floatingActionButton_collectMainFragment_addComment)
            }
        })

        collectViewModel.getPublicWork().observeOnce(viewLifecycleOwner, Observer {
            val typeWork = typeWorkViewModel.getTypeOfWorkFromFlag(it.publicWork.typeWorkFlag)
            typeWork?.let {
                workStatusViewModel.loadWorkStatusFromList(it.getWorkStatusIds())
            }
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

        floatingActionButton_collectMainFragment_addPhoto.setOnClickListener {
            photoViewModel.setPhoto(null)
            toggleMenu()
            navigateTo(R.id.action_collectMainFragment_to_photoAddFragment)
        }

        floatingActionButton_collectMainFragment_addComment.setOnClickListener {
            launchBottomSheetComment()
            toggleMenu()
        }

        floatingActionButton_collectMainFragment_menu.setOnClickListener {
            toggleMenu()
        }
    }

    private fun toggleMenu() {
        collectFragmentViewModel.rotateFabMenu(floatingActionButton_collectMainFragment_menu)
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

    private fun launchWorkStatusDialog() {
        workStatusViewModel.currentWorkStatusList.observeOnce(viewLifecycleOwner,
            Observer { workStatus ->
                val optionsArray = workStatus.map { it.name }.toTypedArray()
                val builder = SelectorDialog.Builder(childFragmentManager)
                builder.withTitle(getString(R.string.dialog_type_photo_title))
                    .withOptions(optionsArray.toList())
                    .withSelectionMode(SelectorDialog.SelectionMode.SINGLE)
                    .withSelectedOptionListener {
                        publicWorkViewModel.updateCurrentPublicWorkStatus(workStatus[it.first()].flag)
                        navigateBack()
                    }
                    .show()
            })
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