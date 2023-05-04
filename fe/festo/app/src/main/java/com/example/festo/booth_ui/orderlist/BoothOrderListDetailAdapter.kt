package com.example.festo.booth_ui.orderlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R

class BoothOrderListDetailAdapter(private var list: MutableList<BoothOrderDetailListData>) :
    RecyclerView.Adapter<BoothOrderListDetailAdapter.ListItemViewHolder>() {
    inner class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var menu: TextView = itemView!!.findViewById(R.id.tv_booth_ordermenu)
        var orderquentity: TextView = itemView!!.findViewById(R.id.tv_booth_orderquantity)

        fun bind(data: BoothOrderDetailListData, position: Int) {
            menu.text = data.menu
            orderquentity.text = data.orderquantity.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoothOrderListDetailAdapter.ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_booth_orderlist_detail, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  list.count()
    }
    override fun onBindViewHolder(holder: BoothOrderListDetailAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}