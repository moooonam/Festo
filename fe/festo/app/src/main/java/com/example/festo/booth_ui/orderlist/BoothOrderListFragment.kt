package com.example.festo.booth_ui.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.customer_ui.home.NotificationFragment
import com.example.festo.databinding.FragmentBoothOrderlistBinding

class BoothOrderListData(
    var id: Int?= null,
    var ordernum: String? = null,
    var state: String? = null,
    var ordertime: String? = null,
    var orderList: String? = null,
    var etcnum: Int? = null,
)
class BoothOrderListFragment : Fragment() {
    private  lateinit var listAdapter: BoothOrderListAdapter
    private var mBinding : FragmentBoothOrderlistBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothOrderlistBinding.inflate(inflater, container, false)

        mBinding = binding

        mBinding!!.orerlistBtn2.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.booth_layout_nav_bottom, BoothOrderListCompleteFragment())
            transaction?.commit()
        }
        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var BoothOrderListDataList: ArrayList<BoothOrderListData> = arrayListOf(
            BoothOrderListData(1,"01","대기","15:00","닭꼬치",2),
            BoothOrderListData(2,"01","준비중","15:00","닭꼬치이잉잉이잉잉이",2)
        )
        listAdapter = BoothOrderListAdapter(BoothOrderListDataList)
        mBinding?.boothOrderlistFragmentListview?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.boothOrderlistFragmentListview?.adapter = listAdapter
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}