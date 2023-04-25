package com.example.festo.customer_ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R


class BoothDetailActivity : AppCompatActivity() {
    // 예시데이터 정의
    var Menulist = arrayListOf<Menu>(
        Menu(com.example.festo.R.drawable.logo1, "이름1", "1000원", false, 0),
        Menu(com.example.festo.R.drawable.logo2, "이름2", "4000원", false, 0),
        Menu(com.example.festo.R.drawable.logo3, "이름3", "3000원", false, 0),
        Menu(com.example.festo.R.drawable.logo1, "이름1", "2000원", false, 0),
        Menu(com.example.festo.R.drawable.logo2, "이름2", "1000원", false, 0),
        Menu(com.example.festo.R.drawable.logo3, "이름3", "5000원", false, 0),
        Menu(com.example.festo.R.drawable.logo1, "이름1", "1000원", false, 0),
        Menu(com.example.festo.R.drawable.logo2, "이름2", "1000원", false, 0),
        Menu(com.example.festo.R.drawable.logo3, "이름3", "1000원", false, 0),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.festo.R.layout.activity_booth_detail)

        // 메뉴 리스트 연결
        val Adapter = MenuAdapter(this, Menulist)
        val list_view = findViewById<ListView>(com.example.festo.R.id.menu_list_view)
        list_view.adapter = Adapter

        val inflater = layoutInflater
//        val item_view = inflater.inflate(R.layout.item_menu, null )
        val view : View = LayoutInflater.from(this).inflate(R.layout.item_menu, null)
        val addBtn = view.findViewById<ImageView>(R.id.menuAdd)
        addBtn.setOnClickListener{
            Toast.makeText(this, "ddddd", Toast.LENGTH_SHORT).show()
            println("여기 클릭!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        }

    }
}