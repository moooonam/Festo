package com.example.festo.customer_ui.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.festo.R
import com.example.festo.data.res.UserOrderListRes

class OrderlistAdapter(private var list: List<UserOrderListRes>) :
    RecyclerView.Adapter<OrderlistAdapter.ListItemViewHolder>() {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var festival: TextView = itemView!!.findViewById(R.id.tv_festivaltitle)
        var date: TextView = itemView!!.findViewById(R.id.tv_orderdate)
        var booth: TextView = itemView!!.findViewById(R.id.tv_boothtitle)
        var menusummary: TextView = itemView!!.findViewById(R.id.tv_menu_summary)
        var ordernumber: TextView = itemView!!.findViewById(R.id.tv_ordernumber)
        var boothImg: ImageView = itemView!!.findViewById(R.id.iv_orderlist_boothImage)
        var orderstate: TextView = itemView!!.findViewById(R.id.tv_orderstate)


        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: UserOrderListRes, position: Int) {
            festival.text = data.festivalName
//            date.text = data.date
            booth.text = data.boothName
            menusummary.text = "${data.productName} 외 ${data.etcCount}건"
            ordernumber.text = "주문번호 ${data.orderNo?.number}번"
            if (data.orderStatus == "WAITING_ACCEPTANCE") {
                orderstate.text = "주문 대기"
            } else if (data.orderStatus ==  "PREPARING_ORDER") {
                orderstate.text = "준비중"
            } else if (data.orderStatus ==  "WAITING_RECEIVE") {
                orderstate.text = "준비 완료"
            } else if (data.orderStatus ==  "COMPLETE") {
                orderstate.text = "픽업 완료"
            } else if (data.orderStatus ==  "CANCELED") {
                orderstate.text = "주문 취소"
            }

            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(boothImg)

        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_orderlist, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: OrderlistAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

