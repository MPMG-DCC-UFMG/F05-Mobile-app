package org.mpmg.mpapp.ui.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.viewmodels.LocationViewModel

class MapFragment : Fragment() {

    private val TAG = MapFragment::class.java.name

    private val locationViewModel by sharedViewModel<LocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()
    }

    private fun setupViewModels() {
        locationViewModel.getCurrentLocationLiveData()
            .observe(viewLifecycleOwner, Observer { location ->
                location ?: return@Observer

                textView_mapFragment_location.text = "Location: ${location.latitude} , ${location.longitude}"
            })
    }
}