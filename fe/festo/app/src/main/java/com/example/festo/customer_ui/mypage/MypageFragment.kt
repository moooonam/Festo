package com.example.festo.customer_ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.customer_ui.home.RecentOrderListData
import com.example.festo.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {
    private lateinit var listAdapter: RecentOrderListAdapter
    private var mBinding : FragmentMypageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        mBinding!!.ivProfile2.setOnClickListener{
            val intent = Intent(getActivity(), BoothMainActivity::class.java)
            startActivity(intent)
        }
        return  mBinding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list : ArrayList<RecentOrderListData> = requireActivity().intent!!.extras!!.get("RecentOrderListDataList") as ArrayList<RecentOrderListData>

        listAdapter = RecentOrderListAdapter(list)
        mBinding?.recentOderListListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.recentOderListListView?.adapter = listAdapter

    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}