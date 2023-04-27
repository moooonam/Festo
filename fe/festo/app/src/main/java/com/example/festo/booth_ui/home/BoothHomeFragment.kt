package com.example.festo.booth_ui.home



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.customer_ui.home.FestivallistFragment
import com.example.festo.databinding.FragmentBoothHomeBinding

class BoothHomeFragment : Fragment() {
    private var mBinding : FragmentBoothHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothHomeBinding.inflate(inflater, container, false)

        mBinding = binding
        mBinding!!.goRegisterboothBtn.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.booth_layout_nav_bottom, RegisterBoothFragment())
            transaction?.commit()}

        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}