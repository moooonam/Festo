package com.example.festo.customer_ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R

@Suppress("DEPRECATION")
class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // 선택된 메뉴 객체 전달받음
        val myOrderList = intent.getSerializableExtra("myOrderList") as ArrayList<*>

        // 어댑터 연결
        var Adapter = MyOrderListAdapter(this, myOrderList as ArrayList<MyOrderList>)
        val list_view = findViewById<ListView>(R.id.list_view)
        list_view.adapter = Adapter

        var totalPrice: Int = 0
        for (order in myOrderList) {
            totalPrice += order.price * order.cnt
        }
        val total = findViewById<TextView>(R.id.totalPrice)
        total.text = "${totalPrice.toString()}원"


        val payBtn = findViewById<TextView>(R.id.payBtn)
        payBtn.setOnClickListener {
            val intent = Intent(this, TosspayActivity::class.java)
            startActivity(intent)
        }
    }
}