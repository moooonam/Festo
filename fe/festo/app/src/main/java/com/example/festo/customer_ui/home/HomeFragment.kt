package com.example.festo.customer_ui.home



import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding
        mBinding!!.testBtn.setOnClickListener {
          /*  val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, FestivallistFragment())
            transaction?.commit()*/
            val intent = Intent(getActivity(), BoothDetailActivity::class.java)
            startActivity(intent)
//            return@setOnClickListener inflater.inflate(R.layout.fragment_festivallist, container, false)
        }

        mBinding!!.notificationBtn.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, NotificationFragment())
            transaction?.commit()
        }


        return mBinding?.root

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}