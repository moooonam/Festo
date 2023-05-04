package com.example.festo.customer_ui.home



import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.festo.R
import com.example.festo.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var mBinding: FragmentHomeBinding? = null
    private var festivalItemData = ArrayList<HomeFestivalList>()
    private lateinit var listAdapter: FestivalItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding
        /*mBinding!!.testBtn.setOnClickListener {
          *//*  val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, FestivallistFragment())
            transaction?.commit()*//*
            val intent = Intent(getActivity(), BoothDetailActivity::class.java)
            startActivity(intent)
//            return@setOnClickListener inflater.inflate(R.layout.fragment_festivallist, container, false)
        }*/

        mBinding!!.notificationBtn.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, NotificationFragment())
            transaction?.commit()
        }

        return mBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var FestivalItemListData: ArrayList<HomeFestivalList> = arrayListOf(
            HomeFestivalList(R.drawable.festival1, "a유등축제"),
            HomeFestivalList(R.drawable.festival2, "b광양 전통숯불구이 축제"),
            HomeFestivalList(R.drawable.festival1, "c유등축제"),
            HomeFestivalList(R.drawable.festival2, "d광양 전통숯불구이 축제"),
            HomeFestivalList(R.drawable.festival1, "e유등축제"),
            HomeFestivalList(R.drawable.festival2, "f광양 전통숯불구이 축제"),
            HomeFestivalList(R.drawable.festival1, "g유등축제"),
            HomeFestivalList(R.drawable.festival2, "h광양 전통숯불구이 축제"),
        )
        listAdapter = FestivalItemListAdapter(FestivalItemListData)
        mBinding?.festivalRecyclerView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.festivalRecyclerView?.adapter = listAdapter
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}