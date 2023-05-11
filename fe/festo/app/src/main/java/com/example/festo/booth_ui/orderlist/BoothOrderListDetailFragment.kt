package com.example.festo.booth_ui.orderlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.data.res.BoothOrderListCompleteRes
import com.example.festo.databinding.FragmentBoothOrderlistDetailBinding

class BoothOrderDetailListData(
    var id: Int?= null,
    var menu:String? = null,
    var orderquantity: Int? = null,

)

class BoothOrderListDetailFragment : Fragment() {
    private  lateinit var listAdapter: BoothOrderListDetailAdapter
    private var mBinding: FragmentBoothOrderlistDetailBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothOrderlistDetailBinding.inflate(inflater, container, false)

        mBinding = binding


        return mBinding?.root
    }
//    companion object  {
//        fun newInstance1(clickedItem: BoothOrderListCompleteRes ): BoothOrderListDetailFragment {
//            val fragment = BoothOrderListDetailFragment()
//            // 매개변수 전달 등의 초기화 작업 수행
//            val args = Bundle().apply {
//                // 필요한 데이터 전달
//                clickedItem.id?.let { putInt("clickedItemId", it) }
//            }
//            fragment.arguments = args
//            return fragment
//        }
//        fun newInstance2(clickedItem: BoothOrderListCompleteData ): BoothOrderListDetailFragment {
//            val fragment = BoothOrderListDetailFragment()
//            // 매개변수 전달 등의 초기화 작업 수행
//            val args = Bundle().apply {
//                // 필요한 데이터 전달
//                clickedItem.id?.let { putInt("clickedItemId", it) }
//            }
//            fragment.arguments = args
//            return fragment
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var BoothOrderDetailListDataList: ArrayList<BoothOrderDetailListData> = arrayListOf(
            BoothOrderDetailListData(1,"닭닭달ㄷ갇랃랃달달다가",1),
            BoothOrderDetailListData(2,"닭닭달ㄷ갇랃랃달달다가",2),
            BoothOrderDetailListData(3,"닭닭달ㄷ갇랃랃달달다가",3),
        )
        listAdapter = BoothOrderListDetailAdapter(BoothOrderDetailListDataList)
        mBinding?.boothOrderlistdetailFragmnetListview?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.boothOrderlistdetailFragmnetListview?.adapter = listAdapter

        val clickedItemId = arguments?.getInt("clickedItemId", -1) ?: -1
        if(clickedItemId != -1) {
            Log.d("아이디값","${clickedItemId}")
        }
    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}