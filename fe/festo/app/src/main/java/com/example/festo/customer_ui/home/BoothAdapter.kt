package com.example.festo.customer_ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.festo.R

class BoothAdapter(val context: Context, val BoothList: ArrayList<Booth>) : BaseAdapter() {
    override fun getCount(): Int {
        return BoothList.size
    }

    override fun getItem(position: Int): Any {
        return BoothList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_booth, null)
//        val logo = view.findViewById<ImageView>(R.id.boothLogoImage)
        val name = view.findViewById<TextView>(R.id.boothName)
        val category = view.findViewById<TextView>(R.id.boothCategory)
        val explanation = view.findViewById<TextView>(R.id.boothExplanation)
        val waitCount = view.findViewById<TextView>(R.id.boothWaitCount)
        val waitTime = view.findViewById<TextView>(R.id.boothWaitTime)
        val accumulation = view.findViewById<TextView>(R.id.boothAccumulation)
        var cardView = view.findViewById<CardView>(R.id.cardView)
        //profile, name, email, content 각 변수를 만들어 item_user.xml의 각 정보를 담을 곳의 위치를 가지게 한다.

        val booth = BoothList[position]
        //user 변수에 배열(또는 서버에서 받아온 데이터)에 담긴 profile, name, email, content 정보를 담아준다.

//        logo.setImageResource(booth.logo)
        name.text = booth.name
        category.text = booth.category
        explanation.text = booth.explanation
        waitCount.text = booth.waitCount
        waitTime.text = booth.waitTime
        accumulation.text = booth.accumulation
        //위에서 가져온 profile, name, email, content 각각의 변수를 만들어둔 카드뷰에 연결시켜준다.


        // 리스트뷰 하나의 아이템 뒷 부분을 눌러도 detail 페이지로 이동
        view.setOnClickListener {
            val intent = Intent(context, BoothDetailActivity::class.java)
//            intent.putExtra("booth_id", booth.id)
            context.startActivity(intent)
        }

        // 카드뷰 클릭시 detail 페이지로 이동
        cardView.setOnClickListener {
            val intent = Intent(context, BoothDetailActivity::class.java)
            context.startActivity(intent)
        }

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}