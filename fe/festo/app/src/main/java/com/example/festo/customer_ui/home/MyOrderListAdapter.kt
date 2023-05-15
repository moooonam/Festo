package com.example.festo.customer_ui.home

import android.content.Context
import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.festo.R
import java.security.AccessControlContext
import java.util.Locale

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
        val menuImage = view.findViewById<ImageView>(R.id.menuImage)
        val name = view.findViewById<TextView>(R.id.menuName)
        val cnt = view.findViewById<TextView>(R.id.menuCnt)
        val price = view.findViewById<TextView>(R.id.menuPrice)
        val cntPrice = view.findViewById<TextView>(R.id.menuCntPrice)

        val myorderlist = myOrderList[position]

        Glide.with(context)
            .load(myorderlist.image)
            .into(menuImage)
        name.text = myorderlist.name
        cnt.text = myorderlist.cnt.toString()
        val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        val formattedString = formatter.format(myorderlist.price)
        price.text = "${formattedString}원"
        val formattedTotal = formatter.format((myorderlist.price * myorderlist.cnt))
        cntPrice.text = "총 ${formattedTotal}원"

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}