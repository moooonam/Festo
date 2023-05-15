package com.example.festo.customer_ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.res.UserNotificationListRes

class NotificationAdapter (private var list: MutableList<UserNotificationListRes>): RecyclerView.Adapter<NotificationAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        var coment: TextView = itemView!!.findViewById(R.id.tv_coment)
        var booth: TextView = itemView!!.findViewById(R.id.tv_booth)
        var date: TextView = itemView!!.findViewById(R.id.tv_date)
        var time: TextView = itemView!!.findViewById(R.id.tv_time)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: UserNotificationListRes, position: Int) {
            coment.text = data.content
            booth.text = "축제: ${data.festivalName}  부스: ${data.boothName}"
            date.text = data.time.substring(0,10)
            time.text = data.time.substring(11)
        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_notification, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: NotificationAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

