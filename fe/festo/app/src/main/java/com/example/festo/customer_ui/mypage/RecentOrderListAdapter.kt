package com.example.festo.customer_ui.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R

class RecentOrderListAdapter (private var list: MutableList<RecentOrderListData>): RecyclerView.Adapter<RecentOrderListAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        var date: TextView = itemView!!.findViewById(R.id.tv_recentOrderDate)
        var booth: TextView = itemView!!.findViewById(R.id.tv_recentOrderBooth)
        var price: TextView = itemView!!.findViewById(R.id.tv_recentOrderPrice)
        var boothImg: ImageView = itemView!!.findViewById(R.id.iv_recentOrderBoothImg)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: RecentOrderListData, position: Int) {
            date.text = data.date
            booth.text = data.booth
            price.text = data.price.toString()
            boothImg.setImageResource(data.boothImg!!)
        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_recentoderlist, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: RecentOrderListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

