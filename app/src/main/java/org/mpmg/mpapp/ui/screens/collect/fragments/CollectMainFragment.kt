package org.mpmg.mpapp.ui.screens.collect.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentCollectMainBinding
import org.mpmg.mpapp.domain.database.models.Photo
import org.mpmg.mpapp.domain.database.models.WorkStatus
import org.mpmg.mpapp.ui.dialogs.CommentsBottomSheetDialog
import org.mpmg.mpapp.ui.dialogs.PickerBottomSheetDialog
import org.mpmg.mpapp.ui.dialogs.WarningDialog
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.collect.adapters.PhotoListAdapter
import org.mpmg.mpapp.ui.screens.collect.viewmodels.CollectFragmentViewModel
import org.mpmg.mpapp.ui.screens.photo.viewmodels.PhotoViewModel
import org.mpmg.mpapp.ui.shared.animation.AnimationHelper
import org.mpmg.mpapp.ui.viewmodels.*

class CollectMainFragment : MVVMFragment<CollectFragmentViewModel, FragmentCollectMainBinding>(),
    PhotoListAdapter.PhotoListAdapterListener {

    private val TAG = CollectMainFragment::class.java.name

    companion object {
        val sessionName = "CollectMainFragmentSession"
        val TAG = CollectMainFragment::class.java.name
    }

    private val session = getKoin().getOrCreateScope(sessionName, named(TAG))
    private val photoViewModel: PhotoViewModel by session.inject()

    private lateinit var navigationController: NavController
    private lateinit var photoListAdapter: PhotoListAdapter
    private lateinit var publicWorkId: String
    private lateinit var workStatuses: List<WorkStatus>

    override val viewModel: CollectFragmentViewModel by session.inject()
    override val layout: Int = R.layout.fragment_collect_main

    private val args: CollectMainFragmentArgs by navArgs()

    override fun initBindings() {
        publicWorkId = args.publicWorkId
        viewModel.startCollectForPublicWork(publicWorkId)
        navigationController = requireActivity().findNavController(R.id.nav_host_fragment)
        binding.collectFragmentViewModel = viewModel
    }

    override fun initObservers() {
        observe(viewModel.photoList) { photoMap ->
            photoListAdapter.updatePhotoList(photoMap.values.toList())
        }

        observe(viewModel.rotated) {
            handleRotation(it)
        }

        observe(viewModel.selectedPublicWork) {
            viewModel.publicWorkLoaded(it.publicWork)
        }

        observe(viewModel.workStatuses) {
            workStatuses = it
        }

        observe(viewModel.navigateBack) {
            it ?: return@observe
            navigateBack(it)
        }

        observe(photoViewModel.photoBundle) {
            it ?: return@observe
            viewModel.processPhotoBundle(it)
        }
    }

    override fun initListeners() {
        with(binding) {
            materialButtonCollectMainFragmentEdit.setOnClickListener {
                editPublicWork()
            }

            floatingActionButtonCollectMainFragmentAddPhoto.setOnClickListener {
                toggleMenu()
                navigateToPhoto(null)
            }

            floatingActionButtonCollectMainFragmentAddComment.setOnClickListener {
                launchBottomSheetComment()
                toggleMenu()
            }

            floatingActionButtonCollectMainFragmentMenu.setOnClickListener {
                toggleMenu()
            }

            floatingActionButtonCollectMainFragmentDeleteCollect.setOnClickListener {
                showDeleteDialog()
            }
        }
    }

    private fun editPublicWork() {
        val action = CollectMainFragmentDirections
            .actionCollectMainFragmentToPublicWorkAddFragment(publicWorkId)
        navigationController.navigate(action)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        setupFABs()
        setupRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed = {
            launchWorkStatusDialog()
        }, enabled = true)
    }

    private fun setupFABs() {
        with(binding) {
            viewModel.initMiniFABs(floatingActionButtonCollectMainFragmentAddComment)
            viewModel.initMiniFABs(floatingActionButtonCollectMainFragmentAddPhoto)
            viewModel.initMiniFABs(floatingActionButtonCollectMainFragmentDeleteCollect)
        }
    }

    private fun handleRotation(rotated: Boolean) {
        with(binding) {
            val buttons = listOf(
                floatingActionButtonCollectMainFragmentAddComment,
                floatingActionButtonCollectMainFragmentDeleteCollect,
                floatingActionButtonCollectMainFragmentAddPhoto
            )
            if (rotated) {
                AnimationHelper.showView(buttons)
            } else {
                AnimationHelper.hideView(buttons)
            }
        }
    }

    private fun setupRecyclerView() {
        initPublicWorkAdapter()
        with(binding.recyclerViewCollectMainFragmentPhotoList) {
            adapter = photoListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initPublicWorkAdapter() {
        photoListAdapter = PhotoListAdapter()
        photoListAdapter.setPhotoListAdapterListener(this)
    }

    private fun showDeleteDialog() {
        val builder = WarningDialog.Builder(childFragmentManager)
        builder.withTitle(getString(R.string.dialog_delete_collect))
            .withMessage(getString(R.string.dialog_delete_collect_message))
            .withOnPositiveClickListener {
                deleteCollect()
            }
            .show()
    }

    private fun deleteCollect() {
        viewModel.deleteCurrentCollect()
    }

    private fun toggleMenu() {
        with(binding) {
            viewModel.rotateFabMenu(floatingActionButtonCollectMainFragmentMenu)
        }
    }

    private fun launchBottomSheetComment() {
        val bottomSheetFragment = CommentsBottomSheetDialog(viewModel)
        bottomSheetFragment.show(
            requireActivity().supportFragmentManager,
            bottomSheetFragment.tag
        )
    }

    private fun launchWorkStatusDialog() {
        val optionsArray = mutableListOf<String>()
        optionsArray.add("----------")
        optionsArray.addAll(workStatuses.map { it.name })
        val builder = PickerBottomSheetDialog.Builder(childFragmentManager)
        builder.withOptions(optionsArray.toList())
            .withNegativeButtonText(getString(R.string.button_cancel_collect))
            .withConfirmButtonText(getString(R.string.button_confirm))
            .withOnPositiveClickListener {
                if (it != 0) {
                    viewModel.updatePublicWorkStatus(workStatuses[it - 1].flag)
                }
                viewModel.updateCollect()
            }
            .withOnNegativeClickListener {
                viewModel.navigateBack(false)
            }
            .show()
    }

    private fun navigateBack(updated: Boolean) {
        if (updated) {
            viewModel.launchSnackbar(getString(R.string.message_collect_updated))
        }

        session.close()
        navigationController.navigateUp()
    }

    override fun onPhotoClicked(photo: Photo) {
        navigateToPhoto(photo)
    }

    private fun navigateToPhoto(photo: Photo?) {
        viewModel.currentCollect.value?.let {
            val action = CollectMainFragmentDirections.actionCollectMainFragmentToPhotoAddFragment(
                collectId = it.id,
                photoId = photo?.id
            )
            navigationController.navigate(action)
        }
    }

}