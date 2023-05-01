package com.example.festo.host_ui.boothlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.databinding.FragmentHostBoothlistBinding

class BoothListFragment : Fragment() {

    private var mBinding: FragmentHostBoothlistBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostBoothlistBinding.inflate(inflater, container, false)

        mBinding = binding
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}