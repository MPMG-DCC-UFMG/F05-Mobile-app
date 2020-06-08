package org.mpmg.mpapp.ui.screens.map

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.DEFAULT_MAP_ZOOM
import org.mpmg.mpapp.core.extensions.toLatLng
import org.mpmg.mpapp.databinding.FragmentMapBinding
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = MapFragment::class.java.name

    private val locationViewModel by sharedViewModel<LocationViewModel>()
    private val publicWorkViewModel by sharedViewModel<PublicWorkViewModel>()

    private lateinit var mapView: MapView
    private var mMap: GoogleMap? = null

    private val firstLocationObserver = Observer<Location?> { location ->
        location ?: return@Observer
        centerCameraOnPosition(location.toLatLng())
        removeFirstLocationObserver()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMapBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        binding.locationViewModel = locationViewModel
        binding.lifecycleOwner = this

        mapView = binding.root.mapView_mapFragment
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        materialButton_mapFragment_confirm.setOnClickListener {
            val location = mMap?.cameraPosition?.target ?: return@setOnClickListener
            publicWorkViewModel.updateCurrPublicWorkLocation(location)
            applyGeocodingAddress()
            navigateBack()
        }

        materialButton_mapFragment_cancel.setOnClickListener {
            navigateBack()
        }

        textView_mapFragment_reverseGeocoding.setOnClickListener {
            it.textView_mapFragment_reverseGeocoding.text = getText(R.string.label_loading_address)
        }

        materialButton_mapFragment_refresh.setOnClickListener {
            searchAddress()
        }
    }

    private fun searchAddress() {
        val cameraCenter = mMap?.cameraPosition?.target ?: return
        locationViewModel.searchAddress(requireContext(),cameraCenter)
    }

    private fun removeFirstLocationObserver() {
        locationViewModel.getCurrentLocationLiveData().removeObserver(firstLocationObserver)
    }

    private fun centerCameraOnPosition(latLng: LatLng) {
        val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM)
        mMap?.animateCamera(cameraUpdate)
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_mapFragment_pop)
        locationViewModel.clearAddress()
    }

    override fun onMapReady(map: GoogleMap?) {
        mMap = map
        mMap?.apply {
            isIndoorEnabled = false

            locationViewModel.getCurrentLocationLiveData()
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

    private fun applyGeocodingAddress() {
        val address = locationViewModel.geocodingAddress.value ?: return
        publicWorkViewModel.fromGeocoding(address)
    }
}