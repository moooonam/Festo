package com.example.festo.booth_ui.orderlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.databinding.FragmentBoothOrderlistBinding
import com.example.festo.databinding.FragmentBoothOrderlistCompleteBinding

class BoothOrderListCompleteData(
    var id: Int?= null,
    var ordernum: String? = null,
    var orderdate: String? = null,
    var ordertime: String? = null,
    var orderList: String? = null,
    var etcnum: Int? = null,
)

class BoothOrderListCompleteFragment : Fragment() {
    private  lateinit var listAdapter: BoothOrderListCompleteAdapter
    private var mBinding : FragmentBoothOrderlistCompleteBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothOrderlistCompleteBinding.inflate(inflater, container, false)

        mBinding = binding

        mBinding!!.orerlistBtn1.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.booth_layout_nav_bottom, BoothOrderListFragment())
            transaction?.commit()
        }
        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var BoothOrderCompleteListDataList: ArrayList<BoothOrderListCompleteData> = arrayListOf(
            BoothOrderListCompleteData(1,"01","2023.05.03","15:00","닭꼬치",2),
            BoothOrderListCompleteData(2,"02","2023.05.03","15:00","닭꼬치이잉이이이이",2),
            BoothOrderListCompleteData(3,"03","2023.05.03","15:00","닭꼬치",2),
        )
        listAdapter = BoothOrderListCompleteAdapter(BoothOrderCompleteListDataList)
        mBinding?.boothOrderlistCompleteFragmentListview?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.boothOrderlistCompleteFragmentListview?.adapter = listAdapter
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}