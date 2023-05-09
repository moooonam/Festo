package com.example.festo.customer_ui.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.customer_ui.home.NotificationFragment
import com.example.festo.customer_ui.mypage.OrderlistAdapter
import com.example.festo.databinding.FragmentOrderlistBinding

class OrderListData(
    var boothImg: Int? = null,
    var festival: String? = null,
    var booth: String? = null,
    var menu: String? = null,
    var ordernum: Int? = null,
    var date: String? = null,
    var state: String? = null,
)
class OrderlistFragment : Fragment() {
    private lateinit var listAdapter: OrderlistAdapter
    private var mBinding : FragmentOrderlistBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentOrderlistBinding.inflate(inflater, container, false)

        mBinding = binding

        // 알림으로 이동
        mBinding!!.notificationBtn.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, NotificationFragment())
            transaction?.commit()
        }

        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var OrderListDataList : ArrayList <OrderListData> = arrayListOf(
           OrderListData(R.drawable.logo1,"광양 숯불구이 축제","까사꼬치","닭꼬치 외 2개",87,"23.04.27 16:10","준비완료"),
           OrderListData(R.drawable.logo1,"광양 숯불구이 축제","까사꼬치","닭꼬치 외 3개",87,"23.04.27 16:10","준비완료"),
           OrderListData(R.drawable.logo1,"광양 숯불구이 축제","까사꼬치","닭꼬치 외 4개",87,"23.04.27 16:10","준비완료"),
        )
        listAdapter = OrderlistAdapter(OrderListDataList)
        mBinding?.orderlistFragmentListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.orderlistFragmentListView?.adapter = listAdapter
    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}