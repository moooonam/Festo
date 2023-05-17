package com.nowusee.festo.booth_ui.orderlist

import RetrofitClient
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nowusee.festo.data.API.BoothAPI
import com.nowusee.festo.data.res.BoothOrderDetailRes
import com.nowusee.festo.data.res.BoothOrderListCompleteRes
import com.nowusee.festo.data.res.BoothOrderListRes
import com.nowusee.festo.data.res.menu
import com.nowusee.festo.databinding.FragmentBoothOrderlistDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoothOrderDetailListData(
    var id: Int?= null,
    var menu:String? = null,
    var orderquantity: Int? = null,

)
//class menu(
//    val menuName : String,
//    val quantity : Int
//)

class BoothOrderListDetailFragment : Fragment() {
    private  lateinit var listAdapter: BoothOrderListDetailAdapter
    private var mBinding: FragmentBoothOrderlistDetailBinding? = null
    private var retrofit = RetrofitClient.client
    private var MenuDetailListData = emptyList<menu>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothOrderlistDetailBinding.inflate(inflater, container, false)

        mBinding = binding


        return mBinding?.root
    }
    companion object  {
        fun newInstance1(clickedItem: BoothOrderListRes ): BoothOrderListDetailFragment {
            val fragment = BoothOrderListDetailFragment()
            // 매개변수 전달 등의 초기화 작업 수행
            val args = Bundle().apply {
                // 필요한 데이터 전달
                clickedItem.orderId?.let { putInt("clickedItemId", it.toInt()) }
            }
            fragment.arguments = args
            return fragment
        }
        fun newInstance2(clickedItem: BoothOrderListCompleteRes ): BoothOrderListDetailFragment {
            val fragment = BoothOrderListDetailFragment()
            // 매개변수 전달 등의 초기화 작업 수행
            val args = Bundle().apply {
                // 필요한 데이터 전달
                clickedItem.orderId?.let { putInt("clickedItemId", it.toInt()) }
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fun getCompleteBoothOrderList() {
            Log.d(" 실행타이밍", "지금")
            val sharedPreferences =
                requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val myValue = sharedPreferences.getString("myToken", "")
            val token = "$myValue"
            val clickedItemId = arguments?.getInt("clickedItemId", -1) ?: -1
            val postApi = retrofit?.create(BoothAPI::class.java)
            postApi!!.getBoothOrderDetail(token, clickedItemId.toString()).enqueue(object :
                Callback<BoothOrderDetailRes> {
                override fun onResponse(call: Call<BoothOrderDetailRes>, response: Response<BoothOrderDetailRes>) {
                    if (response.isSuccessful) {
                        MenuDetailListData = response.body()?.menus!!
                        mBinding?.tvOrdernum!!.text = "${response.body()!!.orderNo.toString()} 번"
                        mBinding?.tvOrderPrice!!.text = "결제금액: ${response.body()!!.totalPrice.toString()}원"

                        listAdapter = BoothOrderListDetailAdapter(MenuDetailListData)
                        mBinding?.boothOrderlistdetailFragmnetListview?.layoutManager =
                            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        mBinding?.boothOrderlistdetailFragmnetListview?.adapter = listAdapter

//                    Log.d("테스트중", "onResponse: ${response.body()}")
                        Log.d(" 부스 디테일 과연", "${response},  ${response.code()}, ${response.body()}")
                    }
                    else {
                        Log.d(" 부스 완료된 주문내역 successful 아닐때", "${response},  ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<BoothOrderDetailRes>, t: Throwable) {
                    Log.d(" 부스 주문내역 실패", "응")
                    t.printStackTrace()
                }
            })
        }
        getCompleteBoothOrderList()

        var BoothOrderDetailListDataList: ArrayList<BoothOrderDetailListData> = arrayListOf(
            BoothOrderDetailListData(1,"닭닭달ㄷ갇랃랃달달다가",1),
            BoothOrderDetailListData(2,"닭닭달ㄷ갇랃랃달달다가",2),
            BoothOrderDetailListData(3,"닭닭달ㄷ갇랃랃달달다가",3),
        )


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