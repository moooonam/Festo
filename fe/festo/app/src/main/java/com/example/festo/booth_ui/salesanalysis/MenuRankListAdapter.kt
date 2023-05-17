package com.example.festo.booth_ui.salesanalysis

import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.festo.R
import com.example.festo.data.res.MenuAnalysis
import java.util.Locale

class MenuRankListAdapter(private var list: List<MenuAnalysis>): RecyclerView.Adapter<MenuRankListAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView = itemView!!.findViewById(R.id.menuImage)
        var name: TextView = itemView!!.findViewById(R.id.menuName)
        val total: TextView = itemView!!.findViewById(R.id.totalSales)
        var count: TextView = itemView!!.findViewById(R.id.totalOrder)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: MenuAnalysis, position: Int) {
            Glide.with(itemView.context)
                .load(data.image_url)
                .into(image)
            name.text = data.name
            count.text = "누적 주문량 : ${data.count.toString()} 건"
            val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
            val formattedString = formatter.format(data.amount)
            total.text = "총 ${formattedString}원"

        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_menu_rank, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: MenuRankListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

