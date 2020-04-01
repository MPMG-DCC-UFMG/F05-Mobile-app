package org.mpmg.mpapp.ui.fragments.publicwork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel

class PublicWorkAddFragment: Fragment() {

    private val TAG = PublicWorkAddFragment::class.java.name

    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_public_work_add, container, false)
    }
}