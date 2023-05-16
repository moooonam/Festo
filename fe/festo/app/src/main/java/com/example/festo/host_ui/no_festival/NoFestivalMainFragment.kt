package com.example.festo.host_ui.no_festival

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.databinding.FragmentNoFestivalMainBinding

class NoFestivalMainFragment : Fragment() {
    private var mBinding: FragmentNoFestivalMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentNoFestivalMainBinding.inflate(inflater, container, false)
        mBinding = binding

        // 축제 등록
        mBinding!!.goRegister.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.no_festival_layout_nav_bottom, RegisterFestivalFragment())
            transaction?.commit()}

        // 일반 유저 마이페이지로 이동
        mBinding!!.goCustomer.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.putExtra("fragment", "MypageFragment")
            startActivity(intent)}
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

