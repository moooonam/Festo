package com.example.festo.booth_ui.orderlist

import RetrofitClient
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.data.API.BoothAPI
import com.example.festo.data.API.OnBoothOrderListCompleteListener
import com.example.festo.data.req.ChangeOrderStateReq
import com.example.festo.data.res.BoothOrderListRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoothOrderListAdapter(
    private var list: MutableList<BoothOrderListRes>,
    private var token: String,
    private val listener: OnBoothOrderListCompleteListener
) :
    RecyclerView.Adapter<BoothOrderListAdapter.ListItemViewHolder>() {

    private var retrofit = RetrofitClient.client
    inner class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!),
        View.OnClickListener {
        //클릭시 이동
        init {
            itemView!!.setOnClickListener(this)
            var changestate_btn = itemView!!.findViewById<TextView>(R.id.btn_changestate)

            changestate_btn.setOnClickListener {
                Log.d("변경버튼", "${changestate_btn.text}")
                var wantState: String = "얍"
                if (changestate_btn.text == "접수") {
                    wantState = "PREPARING_ORDER"
                } else if (changestate_btn.text == "준비완료") {
                    wantState = "WAITING_RECEIVE"
                } else if (changestate_btn.text == "수령완료") {
                    wantState = "COMPLETE"
                }

                fun changeOrderStatus() {
                    Log.d("오더아이디", "${orderId}")
                    Log.d("원하는상태", "${wantState}")
                    val postApi = retrofit?.create(BoothAPI::class.java)
                    postApi!!.changeOrderStatus(
                        token,
                        orderId,
                        ChangeOrderStateReq(wantState)
                    )
                        .enqueue(object : Callback<Long> {
                            override fun onResponse(
                                call: Call<Long>,
                                response: Response<Long>
                            ) {
                                Log.d(
                                    "변경테스트",
                                    "${response.isSuccessful()}, ${response.code()}, ${response}"
                                )
                            }

                            override fun onFailure(call: Call<Long>, t: Throwable) {
                                t.printStackTrace()
                                Log.d("테스트트트트트", "시래패패패패패패패패패패패퍂패패패")
                                listener.onBoothOrderListComplete()
                            }
                        })
                }
                changeOrderStatus()

            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            Log.d("여기","클릭")
            val clickedItem = list[position]

            val fragment = BoothOrderListDetailFragment.newInstance1(clickedItem)
            val fragmentManager = (v?.context as BoothMainActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.booth_layout_nav_bottom, fragment)
                .addToBackStack(null)
                .commit()
        }

        var ordernum: TextView = itemView!!.findViewById(R.id.tv_booth_ordernum)
        var state: TextView = itemView!!.findViewById(R.id.tv_booth_orderstate)
        var orderlist: TextView = itemView!!.findViewById(R.id.tv_booth_orderlist)
        var ordertime: TextView = itemView!!.findViewById(R.id.tv_booth_ordertime)
        var change_state_btn: TextView = itemView!!.findViewById(R.id.btn_changestate)
        var orderId: String = "0"
        // 데어터 넣어주기


        fun bind(data: BoothOrderListRes, position: Int) {
            orderId = data.orderId.toString()
            ordernum.text = data.orderNo.number.toString()
            if (data.orderStatus == "WAITING_ACCEPTANCE") {
                state.text = "접수대기"
            } else if (data.orderStatus == "PREPARING_ORDER") {
                state.text = "준비중"
            } else if (data.orderStatus == "WAITING_RECEIVE") {
                state.text = "준비완료"
            } else {
                change_state_btn.text = "준비완료"
            }
            orderlist.text = "${data.firstMenuName} 외 ${data.etcCount.toString()}개"
            var time = data.time
            ordertime.text = time.replace("/", "  ")
            if (data.orderStatus == "WAITING_ACCEPTANCE") {
                change_state_btn.text = "접수"
            } else if (data.orderStatus == "PREPARING_ORDER") {
                change_state_btn.text = "준비완료"
            } else if (data.orderStatus == "WAITING_RECEIVE") {
                change_state_btn.text = "수령완료"
            } else {
                change_state_btn.text = "준비완료"
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_boothorderlist, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: BoothOrderListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}
