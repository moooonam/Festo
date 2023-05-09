package com.example.festo.customer_ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.festo.R
import java.security.AccessControlContext

class MyOrderListAdapter(val context: Context, val myOrderList: ArrayList<MyOrderList>) : BaseAdapter() {
    override fun getCount(): Int {
        return myOrderList.size
    }

    override fun getItem(position: Int): Any {
        return myOrderList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_ordermenu, null)
        val image = view.findViewById<ImageView>(R.id.menuImage)
        val name = view.findViewById<TextView>(R.id.menuName)
        val cnt = view.findViewById<TextView>(R.id.menuCnt)
        val price = view.findViewById<TextView>(R.id.menuPrice)
        val cntPrice = view.findViewById<TextView>(R.id.menuCntPrice)

        val myorderlist = myOrderList[position]

//        image.setImageResource(myorderlist.image)
        name.text = myorderlist.name
        cnt.text = myorderlist.cnt.toString()
        price.text = "${myorderlist.price}원"
        cntPrice.text = "총 ${(myorderlist.price * myorderlist.cnt)}원"

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}