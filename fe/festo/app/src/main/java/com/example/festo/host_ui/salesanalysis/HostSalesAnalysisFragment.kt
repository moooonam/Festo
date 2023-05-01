package com.example.festo.host_ui.salesanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.databinding.FragmentBoothSalesanalysisBinding
import com.example.festo.databinding.FragmentHostSalesanalysisBinding

class HostSalesAnalysisFragment : Fragment() {

    private var mBinding : FragmentHostSalesanalysisBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostSalesanalysisBinding.inflate(inflater, container, false)

        mBinding = binding
        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}