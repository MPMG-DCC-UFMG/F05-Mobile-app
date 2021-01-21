package org.mpmg.mpapp.ui.screens.map

import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.DEFAULT_MAP_ZOOM
import org.mpmg.mpapp.core.extensions.toLatLng
import org.mpmg.mpapp.databinding.FragmentMapBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class MapFragment : MVVMFragment<LocationViewModel, FragmentMapBinding>(), OnMapReadyCallback {

    private val TAG = MapFragment::class.java.name

    override val viewModel: LocationViewModel by sharedViewModel<LocationViewModel>()
    override val layout: Int = R.layout.fragment_map

    private lateinit var mapView: MapView
    private var mMap: GoogleMap? = null

    private val firstLocationObserver = Observer<Location?> { location ->
        location ?: return@Observer
        centerCameraOnPosition(location.toLatLng())
        removeFirstLocationObserver()
    }

    override fun initBindings() {
        binding.locationViewModel = viewModel
    }

    override fun initViews(savedInstanceState: Bundle?) {
        initMap(savedInstanceState)
    }

    override fun initListeners() {
        with(binding) {
            materialButtonMapFragmentConfirm.setOnClickListener {
                val location = mMap?.cameraPosition?.target ?: return@setOnClickListener
                viewModel.setSelectedLocation(location)
                viewModel.cleanCurrentAddress()
                navigateBack()
            }

            materialButtonMapFragmentCancel.setOnClickListener {
                viewModel.cleanLocations()
                navigateBack()
            }

            textViewMapFragmentReverseGeocoding.setOnClickListener {
                textViewMapFragmentReverseGeocoding.text =
                    getText(R.string.label_loading_address)
            }

            materialButtonMapFragmentRefresh.setOnClickListener {
                searchAddress()
            }
        }
    }

    private fun initMap(savedInstanceState: Bundle?) {
        with(binding) {
            mapView = mapViewMapFragment
            mapView.apply {
                onCreate(savedInstanceState)
                onResume()
                try {
                    MapsInitializer.initialize(activity?.applicationContext)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                getMapAsync(::onMapReady)
            }
        }
    }

    private fun searchAddress() {
        val cameraCenter = mMap?.cameraPosition?.target ?: return
        viewModel.searchAddress(requireContext(), cameraCenter)
    }

    private fun removeFirstLocationObserver() {
        viewModel.getCurrentLocationLiveData().removeObserver(firstLocationObserver)
    }

    private fun centerCameraOnPosition(latLng: LatLng) {
        val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM)
        mMap?.animateCamera(cameraUpdate)
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_mapFragment_pop)
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        mMap?.apply {
            isIndoorEnabled = false
            viewModel.getCurrentLocationLiveData()
                .observe(viewLifecycleOwner, firstLocationObserver)
        }
    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}