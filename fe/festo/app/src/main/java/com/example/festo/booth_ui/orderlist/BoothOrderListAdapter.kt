package com.example.festo.booth_ui.orderlist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.booth_ui.orderlist.BoothOrderListData
import com.example.festo.data.res.BoothOrderListRes
import com.kakao.sdk.common.KakaoSdk.init

class BoothOrderListAdapter(private var list: MutableList<BoothOrderListRes>) :
    RecyclerView.Adapter<BoothOrderListAdapter.ListItemViewHolder>() {

    inner class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!),
        View.OnClickListener {
        //클릭시 이동
        init {
            itemView!!.setOnClickListener(this)
            itemView!!.findViewById<TextView>(R.id.btn_changestate).setOnClickListener {
                Log.d("어탭터", "버튼 클릭이벤트")
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

//            val clickedItem = list[position]
//
//            val fragment = BoothOrderListDetailFragment.newInstance1(clickedItem)
//            val fragmentManager = (v?.context as BoothMainActivity).supportFragmentManager
//            fragmentManager.beginTransaction().replace(R.id.booth_layout_nav_bottom, fragment)
//                .addToBackStack(null)
//                .commit()
        }

        var ordernum: TextView = itemView!!.findViewById(R.id.tv_booth_ordernum)
        var state: TextView = itemView!!.findViewById(R.id.tv_booth_orderstate)
        var orderlist: TextView = itemView!!.findViewById(R.id.tv_booth_orderlist)
        var ordertime: TextView = itemView!!.findViewById(R.id.tv_booth_ordertime)
        var change_state_btn: TextView = itemView!!.findViewById(R.id.btn_changestate)
        // 데어터 넣어주기


        fun bind(data: BoothOrderListRes, position: Int) {
            ordernum.text = data.orderNo.number.toString()
            if (data.orderStatus == "WAITING_ACCEPTANCE") {
                state.text = "접수대기"
            }
            orderlist.text = "${data.firstMenuName} 외 ${data.etcCount.toString()}개"
            ordertime.text = data.time
            if (data.orderStatus == "WAITING_ACCEPTANCE") {
                change_state_btn.text = "접수"
            } else  {
                change_state_btn.text = "준비완료"
            }
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_boothorderlist, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: BoothOrderListAdapter.ListItemViewHolder, position: Int) {
        holder.bind(list[position], position)
    }
}
