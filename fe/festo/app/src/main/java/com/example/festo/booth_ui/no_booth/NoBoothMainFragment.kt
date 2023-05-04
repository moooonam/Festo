package com.example.festo.booth_ui.no_booth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.databinding.FragmentNoBoothMainBinding

class NoBoothMainFragment : Fragment() {
    private var mBinding: FragmentNoBoothMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentNoBoothMainBinding.inflate(inflater, container, false)
        mBinding = binding

        // 부스 등록 페이지로 이동
        mBinding!!.goRegister.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.no_booth_layout_nav_bottom, RegisterBoothFragment())
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

