package com.example.festo.booth_ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.res.BoothMenuListRes

class MenuListAdapter(private var list: List<BoothMenuListRes>): RecyclerView.Adapter<MenuListAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        //        var image: TextView = itemView!!.findViewById(R.id.menuImage)
        var name: TextView = itemView!!.findViewById(R.id.menuName)
        var price: TextView = itemView!!.findViewById(R.id.menuPrice)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: BoothMenuListRes, position: Int) {
//            image.setImageResource(data.image!!)
            name.text = data.name
            price.text = data.price.toString()
        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_booth_menulist, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: MenuListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

