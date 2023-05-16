package com.example.festo.booth_ui.orderlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.res.BoothOrderDetailRes
import com.example.festo.data.res.menu

class BoothOrderListDetailAdapter(private var list: List<menu>) :
    RecyclerView.Adapter<BoothOrderListDetailAdapter.ListItemViewHolder>() {
    inner class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var menuname: TextView = itemView!!.findViewById(R.id.tv_booth_ordermenu)
        var orderquentity: TextView = itemView!!.findViewById(R.id.tv_booth_orderquantity)

        fun bind(data: menu, position: Int) {
            menuname.text = data.menuName
            orderquentity.text = data.quantity.toString()
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