package com.example.festo.customer_ui.orderlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.customer_ui.home.NotificationFragment
import com.example.festo.customer_ui.mypage.OrderlistAdapter
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.UserOrderListRes
import com.example.festo.databinding.FragmentOrderlistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderlistFragment : Fragment() {
    private var retrofit = RetrofitClient.client
    private var orderlist = emptyList<UserOrderListRes>()
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

        // 유저의 주문내역 조회
        val postApi = retrofit?.create(UserAPI::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        println(token)
        postApi!!.getOrderList(token).enqueue(object : Callback<List<UserOrderListRes>> {
            override fun onResponse(
                call: Call<List<UserOrderListRes>>,
                response: Response<List<UserOrderListRes>>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body()?.size)
                    // Log.d(" 주문내역", "${response.body()?.get(0)?.orderNo?.number}")
                    orderlist = response.body() ?: emptyList()
                    orderlist = orderlist.reversed()
                    listAdapter = OrderlistAdapter(orderlist)
                    mBinding?.orderlistFragmentListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    mBinding?.orderlistFragmentListView?.adapter = listAdapter
                }
            }

            override fun onFailure(call: Call<List<UserOrderListRes>>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })
    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}