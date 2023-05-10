package com.example.festo.booth_ui.orderlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.API.ApiService
import com.example.festo.data.API.BoothAPI
import com.example.festo.data.res.BoothOrderListCompleteRes
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.TestUser
import com.example.festo.databinding.FragmentBoothOrderlistBinding
import com.example.festo.databinding.FragmentBoothOrderlistCompleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    private var completeOrderListData = emptyList<BoothOrderListCompleteRes>()
    private var retrofit = RetrofitClient.client
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
        fun getCompleteBoothOrderList() {
            Log.d(" 실행타이밍", "지금")
            val postApi = retrofit?.create(BoothAPI::class.java)
            postApi!!.getBoothOrderListComplete("1").enqueue(object : Callback<List<BoothOrderListCompleteRes>> {
                override fun onResponse(call: Call<List<BoothOrderListCompleteRes>>, response: Response<List<BoothOrderListCompleteRes>>) {
                    if (response.isSuccessful) {
                        completeOrderListData = response.body()!!
//                    Log.d("테스트중", "onResponse: ${response.body()}")
                        Log.d(" 부스 완료된 주문내역 과연", "${response},  ${response.code()}")
                    }
                    else {
                        Log.d(" 부스 완료된 주문내역 successful 아닐때", "${response},  ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<List<BoothOrderListCompleteRes>>, t: Throwable) {
                    Log.d(" 부스 주문내역 실패", "응")
                    t.printStackTrace()
                }
            })
        }
        getCompleteBoothOrderList()
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