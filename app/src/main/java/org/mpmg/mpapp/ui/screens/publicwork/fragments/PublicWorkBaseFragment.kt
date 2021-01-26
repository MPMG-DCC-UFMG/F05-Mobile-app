package org.mpmg.mpapp.ui.screens.publicwork.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.databinding.FragmentBaseBinding
import org.mpmg.mpapp.databinding.ItemMenuBinding
import org.mpmg.mpapp.ui.components.MenuItemModel
import org.mpmg.mpapp.ui.screens.publicwork.fragments.list.PublicWorkListFragment
import org.mpmg.mpapp.ui.screens.publicwork.models.BottomMenu
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.BottomMenuViewModel


class PublicWorkBaseFragment : Fragment() {

    private val TAG = PublicWorkBaseFragment::class.java.name

    lateinit var navController: NavController
    lateinit var mainNavController: NavController

    private lateinit var binding: FragmentBaseBinding

    private val viewModel: BottomMenuViewModel by viewModel()

    private val session = getKoin().getOrCreateScope(
        PublicWorkListFragment.sessionName, named(
            PublicWorkListFragment.TAG
        )
    )

    override fun onDestroy() {
        session.close()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_base, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNavigationController()
        initMenu()
        setupObservers()
    }

    private fun setupObservers() {
        observe(viewModel.selectedOption) {
            handleNavigation(it)
        }
    }

    private fun handleNavigation(option: BottomMenu) {
        when (option) {
            BottomMenu.HOME -> mainNavController.navigateUp()
            BottomMenu.LIST -> navController.navigate(R.id.publicWorkListFragment)
            BottomMenu.FILTER -> navController.navigate(R.id.publicWorkFilterFragment)
        }
    }

    private fun setNavigationController() {
        navController = requireActivity().findNavController(R.id.navHost_baseFragment)
        mainNavController = requireActivity().findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChanges(destination.id)
        }
    }

    private fun handleDestinationChanges(destinationId: Int) {
        val icon = when (destinationId) {
            R.id.publicWorkFilterFragment -> BottomMenu.FILTER
            R.id.publicWorkListFragment -> BottomMenu.LIST
            else -> BottomMenu.HOME
        }
        viewModel.updateSelectedOption(icon)
    }

    private fun initMenu() {
        with(binding.bottomNavigationViewBaseFragment) {
            viewModel.navigationOptions.forEach { option ->
                addView(createMenuItem(option))
            }
        }
    }

    private fun getParams() = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT,
        1.0f
    )

    private fun createMenuItem(menuItemModel: MenuItemModel): View {
        val child: ItemMenuBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_menu, null, false)
        with(child) {
            menuItem = menuItemModel
            menuItemContainer.layoutParams = getParams()
            menuItemContainer.setOnClickListener {
                viewModel.selectOption(menuItemModel.type)
            }
        }

        return child.root
    }
}