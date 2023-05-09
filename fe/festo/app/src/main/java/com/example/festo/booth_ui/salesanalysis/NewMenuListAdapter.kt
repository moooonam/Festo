package com.example.festo.booth_ui.salesanalysis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R

class NewMenuListAdapter(private var list: ArrayList<NewMenuData>): RecyclerView.Adapter<NewMenuListAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        //        var image: TextView = itemView!!.findViewById(R.id.boothImage)
        var name: TextView = itemView!!.findViewById(R.id.menuName)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: NewMenuData, position: Int) {
//            image.setImageResource(data.image!!)
            name.text = data.name
        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_new_menu, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: NewMenuListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

