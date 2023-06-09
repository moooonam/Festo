package com.nowusee.festo.booth_ui.orderlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nowusee.festo.R
import com.nowusee.festo.data.API.BoothAPI
import com.nowusee.festo.data.res.BoothOrderListCompleteRes
import com.nowusee.festo.databinding.FragmentBoothOrderlistCompleteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



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
            // 전달받은 부스 아이디
            val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val boothId = sharedPreferences.getString("boothId", "")
            val myValue = sharedPreferences.getString("myToken", "")
            val token = "$myValue"
            val postApi = retrofit?.create(BoothAPI::class.java)
            postApi!!.getBoothOrderListComplete(token,boothId.toString()).enqueue(object : Callback<List<BoothOrderListCompleteRes>> {
                override fun onResponse(call: Call<List<BoothOrderListCompleteRes>>, response: Response<List<BoothOrderListCompleteRes>>) {
                    if (response.isSuccessful) {
                        completeOrderListData = response.body()!!
                        completeOrderListData = completeOrderListData.reversed()
                        if (completeOrderListData.size != 0) {
                            listAdapter = BoothOrderListCompleteAdapter(completeOrderListData as MutableList<BoothOrderListCompleteRes>)
                            mBinding?.boothOrderlistCompleteFragmentListview?.layoutManager =
                                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                            mBinding?.boothOrderlistCompleteFragmentListview?.adapter = listAdapter
                        }
//                    Log.d("테스트중", "onResponse: ${response.body()}")
                        Log.d(" 부스 완료된 주문내역 과연", "${response},  ${response.code()}, ${completeOrderListData}")
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

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}