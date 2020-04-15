package org.mpmg.mpapp.ui.fragments.map

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.core.Constants.DEFAULT_MAP_ZOOM
import org.mpmg.mpapp.core.extensions.toLatLng
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private val TAG = MapFragment::class.java.name

    private val locationViewModel by sharedViewModel<LocationViewModel>()

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
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        mapView = view.mapView_mapFragment
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun removeFirstLocationObserver() {
        locationViewModel.getCurrentLocationLiveData().removeObserver(firstLocationObserver)
    }

    private fun centerCameraOnPosition(latLng: LatLng) {
        val cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_MAP_ZOOM)
        mMap?.animateCamera(cameraUpdate)
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
}