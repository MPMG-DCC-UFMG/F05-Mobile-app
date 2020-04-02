package org.mpmg.mpapp.ui.fragments.publicwork.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.mpmg.mpapp.R

class PublicWorkSendFragment: Fragment() {

    private val TAG = PublicWorkSendFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_public_work_send, container, false)
    }
}