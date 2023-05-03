package com.example.festo.host_ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.databinding.FragmentHostHomeBinding
import com.example.festo.host_ui.no_festival.RegisterFestivalFragment

class HostHomeFragment : Fragment() {
    private var mBinding: FragmentHostHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostHomeBinding.inflate(inflater, container, false)
        mBinding = binding
        mBinding!!.goRegisterfestivalBtn.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.host_layout_nav_bottom, RegisterFestivalFragment())
            transaction?.commit()}
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

