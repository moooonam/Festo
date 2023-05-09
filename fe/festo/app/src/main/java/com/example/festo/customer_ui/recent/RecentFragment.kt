package com.example.festo.customer_ui.recent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.customer_ui.home.NotificationFragment
import com.example.festo.databinding.FragmentRecentBinding

class RecentFragment : Fragment() {

    private var mBinding : FragmentRecentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var binding = FragmentRecentBinding.inflate(inflater, container, false)

        mBinding = binding

        // 알림으로 이동
        mBinding!!.notificationBtn.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, NotificationFragment())
            transaction?.commit()
        }
        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}