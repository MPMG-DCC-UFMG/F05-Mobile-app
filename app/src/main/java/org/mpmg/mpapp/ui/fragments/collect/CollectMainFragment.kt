package org.mpmg.mpapp.ui.fragments.collect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.mpmg.mpapp.R

class CollectMainFragment : Fragment() {

    private val TAG = CollectMainFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collect_main, container, false)
    }
}