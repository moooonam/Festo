package com.nowusee.festo.host_ui.boothlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nowusee.festo.R
import com.nowusee.festo.data.res.BoothListRes

class BoothlistAdapter(private var list: List<BoothListRes>): RecyclerView.Adapter<BoothlistAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView = itemView!!.findViewById(R.id.boothImage)
        var name: TextView = itemView!!.findViewById(R.id.boothName)
        var explanation: TextView = itemView!!.findViewById(R.id.boothExplanation)


        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: BoothListRes, position: Int) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(image)
            name.text = data.name
            explanation.text = data.description
        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_boothlist, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: BoothlistAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

