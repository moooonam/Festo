package com.example.festo.customer_ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R


class FestivalActivity : AppCompatActivity() {
    // 예시 데이터 정의
    var BoothList = arrayListOf<Booth>(
        Booth(R.drawable.logo1, "이름1", "카테고리1", "엄청맛있어욥", "5", "10", "12"),
        Booth(R.drawable.logo2, "이름2", "카테고리2", "우와아아아아", "7", "25", "27"),
        Booth(R.drawable.logo3, "이름3", "카테고리3", "잠온다", "1", "5", "10"),
        Booth(R.drawable.logo1, "이름1", "카테고리1", "엄청맛있어욥", "5", "10", "12"),
        Booth(R.drawable.logo2, "이름2", "카테고리2", "우와아아아아", "7", "25", "27"),
        Booth(R.drawable.logo3, "이름3", "카테고리3", "잠온다", "1", "5", "10"),
        Booth(R.drawable.logo1, "이름1", "카테고리1", "엄청맛있어욥", "5", "10", "12"),
        Booth(R.drawable.logo2, "이름2", "카테고리2", "우와아아아아", "7", "25", "27"),
        Booth(R.drawable.logo3, "이름3", "카테고리3", "잠온다", "1", "5", "10"),
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.festo.R.layout.activity_festival)

        // 하나의 축제에 대한 부스 리스트 어댑터 연결
        val Adapter = BoothAdapter(this, BoothList)
        val list_view = findViewById<ListView>(com.example.festo.R.id.list_view)
        list_view.adapter = Adapter


        // 리스트뷰 클릭 상세피이지 이동
//        list_view.onItemClickListener = AdapterView.OnItemClickListener {
//                parent,
//                view,
//                position,
//                id -> val selectItem = parent.getItemAtPosition(position) as Booth
//            val booth = selectItem.name
//            val intent = Intent(this, BoothDetailActivity::class.java)
//
//            // 부스 이름을 담아서 부스 디테일 액티비티로 이동
//            intent.putExtra("boothInfo", booth);
//            startActivity(intent)
//        }


    }
}