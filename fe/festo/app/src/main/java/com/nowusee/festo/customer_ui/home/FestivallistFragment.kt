package com.nowusee.festo.customer_ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nowusee.festo.databinding.FragmentFestivallistBinding


class FestivallistFragment : Fragment() {

    private var mBinding : FragmentFestivallistBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding = FragmentFestivallistBinding.inflate(inflater, container, false)
        mBinding = binding
        return  mBinding?.root

    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}