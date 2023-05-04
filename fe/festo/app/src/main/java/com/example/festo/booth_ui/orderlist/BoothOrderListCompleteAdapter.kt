package com.example.festo.booth_ui.orderlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity

class BoothOrderListCompleteAdapter(private var list: MutableList<BoothOrderListCompleteData>) :
    RecyclerView.Adapter<BoothOrderListCompleteAdapter.ListItemViewHolder>() {
    inner class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!),
        View.OnClickListener {
        init {
            itemView!!.setOnClickListener(this)
            }
        override fun onClick(v: View?) {
            val position = adapterPosition

            val clickedItem = list[position]

            val fragment = BoothOrderListDetailFragment.newInstance2(clickedItem)
            val fragmentManager = (v?.context as BoothMainActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.booth_layout_nav_bottom, fragment)
                .addToBackStack(null)
                .commit()
        }
        var ordernum: TextView = itemView!!.findViewById(R.id.tv_booth_ordernum_complete)
        var orderdate: TextView = itemView!!.findViewById(R.id.tv_booth_orderdate_complete)
        var ordertime: TextView = itemView!!.findViewById(R.id.tv_booth_ordertime_complete)
        var orderlist: TextView = itemView!!.findViewById(R.id.tv_booth_orderlist_complete)
        fun bind(data: BoothOrderListCompleteData, position: Int) {
            ordernum.text = data.ordernum
            orderdate.text = data.orderdate
            ordertime.text = data.ordertime
            orderlist.text = data.orderList
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_booth_orderlist_complete, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(
        holder: BoothOrderListCompleteAdapter
        .ListItemViewHolder, position: Int
    ) {
        holder.bind(list[position], position)
    }
}