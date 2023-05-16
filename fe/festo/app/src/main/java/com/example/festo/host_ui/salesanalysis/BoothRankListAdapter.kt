package com.example.festo.host_ui.salesanalysis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R

class BoothRankListAdapter(private var list: ArrayList<BoothRankData>): RecyclerView.Adapter<BoothRankListAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView = itemView!!.findViewById(R.id.boothImage)
        var name: TextView = itemView!!.findViewById(R.id.boothName)
        var category: TextView = itemView!!.findViewById(R.id.boothCategory)
        var boothNum: TextView = itemView!!.findViewById(R.id.boothNum)
        var totalOrder: TextView = itemView!!.findViewById(R.id.totalOrder)
        var totalSales : TextView = itemView!!.findViewById(R.id.totalSales)

        // onBindViewHolder의 역할을 대신한다.
        fun bind(data: BoothRankData, position: Int) {
            image.setImageResource(data.image!!)
            name.text = data.name
            category.text = data.category
            boothNum.text = data.boothNum.toString()
            totalOrder.text = data.totalOrder.toString()
            totalSales.text = "총 매출액: ${data.totalSales}원"
        }
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_booth_rank, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: BoothRankListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

}

