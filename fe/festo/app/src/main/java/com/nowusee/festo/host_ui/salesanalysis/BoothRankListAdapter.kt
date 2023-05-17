package com.nowusee.festo.host_ui.salesanalysis

import android.annotation.SuppressLint
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nowusee.festo.R
import com.nowusee.festo.data.res.BoothData
import java.util.Locale

class BoothRankListAdapter(private var list: List<BoothData>): RecyclerView.Adapter<BoothRankListAdapter.ListItemViewHolder> () {

    // inner class로 ViewHolder 정의
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        var image: ImageView = itemView!!.findViewById(R.id.boothImage)
        var name: TextView = itemView!!.findViewById(R.id.boothName)
        var totalSales : TextView = itemView!!.findViewById(R.id.totalSales)
        var totalOrder : TextView = itemView!!.findViewById(R.id.totalOrder)

        // onBindViewHolder의 역할을 대신한다.
        @SuppressLint("SetTextI18n")
        fun bind(data: BoothData, position: Int) {
            Glide.with(itemView.context)
                .load(data.image_url)
                .into(image)
            name.text = data.booth_name
            totalOrder.text = "총 주문 수 : ${data.count.toString()} 건"
            val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
            val formattedString = formatter.format(data.amount)
            totalSales.text = "부스 총 매출 : ${formattedString}원"
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

    fun updateList(newList: List<BoothData>) {
        list = newList
        notifyDataSetChanged()
    }

}

