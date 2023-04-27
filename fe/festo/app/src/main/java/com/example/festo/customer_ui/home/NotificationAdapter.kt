package com.example.festo.customer_ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R

class NotificationAdapter (private var list: MutableList<NotificationData>): RecyclerView.Adapter<NotificationAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        var state: TextView = itemView!!.findViewById(R.id.tv_state)
        var booth: TextView = itemView!!.findViewById(R.id.tv_booth)
        var date: TextView = itemView!!.findViewById(R.id.tv_date)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: NotificationData, position: Int) {
            state.text = data.getState()
            booth.text = data.getBooth()
            date.text = data.getDate()
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

