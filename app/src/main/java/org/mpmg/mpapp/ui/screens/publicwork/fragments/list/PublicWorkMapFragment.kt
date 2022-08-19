package org.mpmg.mpapp.ui.screens.publicwork.fragments.list

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterManager
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants
import org.mpmg.mpapp.core.extensions.observe
import org.mpmg.mpapp.core.extensions.toLatLng
import org.mpmg.mpapp.databinding.FragmentPublicWorkMapBinding
import org.mpmg.mpapp.ui.screens.base.MVVMFragment
import org.mpmg.mpapp.ui.screens.publicwork.models.MapClusterItem
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkListViewModel
import org.mpmg.mpapp.ui.screens.publicwork.viewmodels.PublicWorkMapViewModel
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class PublicWorkMapFragment :
    MVVMFragment<PublicWorkMapViewModel, FragmentPublicWorkMapBinding>(), OnMapReadyCallback {


    private val session = getKoin().getScope(PublicWorkListFragment.sessionName)

    override val viewModel: PublicWorkMapViewModel by viewModel()
    override val layout: Int = R.layout.fragment_public_work_map

    private val publicWorkListViewModel: PublicWorkListViewModel by session.inject()
    private val locationViewModel: LocationViewModel by sharedViewModel()

    private lateinit var mapView: MapView
    private var mMap: GoogleMap? = null
    private lateinit var clusterManager: ClusterManager<MapClusterItem>

    private val firstLocationObserver = Observer<Location?> { location ->
        location ?: return@Observer
        centerCameraOnPosition(location.toLatLng())
        removeFirstLocationObserver()
    }

    private fun removeFirstLocationObserver() {
        locationViewModel.getCurrentLocationLiveData().removeObserver(firstLocationObserver)
    }

    private fun centerCameraOnPosition(latLng: LatLng) {
        val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(
            latLng,
            Constants.DEFAULT_MAP_ZOOM
        )
        mMap?.animateCamera(cameraUpdate)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        initMap(savedInstanceState)
    }

    private fun initMap(savedInstanceState: Bundle?) {
        with(binding) {
            mapView = mapViewPublicWorkMapFragment
            mapView.apply {
                onCreate(savedInstanceState)
                onResume()
                try {
                    MapsInitializer.initialize(requireActivity().applicationContext)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                getMapAsync(::onMapReady)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        clusterManager = ClusterManager<MapClusterItem>(requireContext(), mMap)
        mMap?.apply {
            isIndoorEnabled = false
            try {
                isMyLocationEnabled = true
            } catch (e: SecurityException) {
                e.message?.let { Log.e("Exception: %s", it) }
            }
            locationViewModel.getCurrentLocationLiveData()
                .observe(viewLifecycleOwner, firstLocationObserver)
            this.setOnCameraIdleListener(clusterManager)
            this.setOnMarkerClickListener(clusterManager)
            setupObservers()
        }
    }

    private fun setupObservers() {
        observe(publicWorkListViewModel.publicWorkMediatedList) {
            viewModel.applyPublicWorkList(it)
        }

        observe(viewModel.mapPublicWorkList) {
            clusterManager.clearItems()
            clusterManager.addItems(it)
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