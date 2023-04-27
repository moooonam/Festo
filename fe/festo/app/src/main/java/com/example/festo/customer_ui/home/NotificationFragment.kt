package com.example.festo.customer_ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.databinding.FragmentNotificationBinding

class NotificationFragment : Fragment() {

    private lateinit var listAdapter: NotificationAdapter

    private var mBinding : FragmentNotificationBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentNotificationBinding.inflate(inflater, container, false)
        mBinding = binding
        return  mBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list : ArrayList<NotificationData> = requireActivity().intent!!.extras!!.get("NotifictionDataList") as ArrayList<NotificationData>

        listAdapter = NotificationAdapter(list)
        mBinding?.notificationListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.notificationListView?.adapter = listAdapter

    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}