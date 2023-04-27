package com.example.festo.customer_ui.home


import android.content.ClipData.Item
import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.mypage.MypageFragment
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.customer_ui.recent.RecentFragment
import com.example.festo.customer_ui.search.SearchFragment
import com.example.festo.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
// 알림페이지 데이터
class NotificationData(
    private var state: String? = null,
    private var booth: String? = null,
    private var date : String? = null,
) {
    fun getState(): String? {
        return state
    }
    fun getBooth(): String? {
        return booth
    }
    fun getDate(): String? {
        return date
    }
}

class RecentOrderListData(
    private var date: String? = null,
    private var booth: String? = null,
    private var price: Int? = null,
    private var boothImg: Int? = null,
){
    fun getDate() : String? {
        return date
    }
    fun getBooth() : String? {
        return booth
    }
    fun getPrice() : Int? {
        return price
    }
    fun getBoothImg() : Int? {
        return boothImg
    }
}
class HomeActivity : AppCompatActivity() {
    // 알림페이지 데이터
    var NotifictionDataList: ArrayList<NotificationData> = arrayListOf(
        NotificationData("첫번째데이터1", "두번째데이터1", "2023년4월26일"),
        NotificationData("첫번째데이터2", "두번째데이터2","2023년4월26일"),
        NotificationData("첫번째데이터3", "두번째데이터3","2023년4월26일"),
        NotificationData("첫번째데이터4", "두번째데이터4","2023년4월26일"),
        NotificationData("첫번째데이터5", "두번째데이터5","2023년4월26일"),
    )

    var RecentOrderListDataList : ArrayList <RecentOrderListData> = arrayListOf(
        RecentOrderListData("23.04.26", "광야야야야야앙",23000, R.drawable.logo1),
        RecentOrderListData("23.04.27", "광야야야야야앙",23000, R.drawable.logo1),
        RecentOrderListData("23.04.28", "광야야야야야앙",23000, R.drawable.logo2),
    )

    // 마이페이지 최근주문 목록 데이터

    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, HomeFragment())
            .commit()
        intent.putExtra("NotifictionDataList", NotifictionDataList)
        intent.putExtra("RecentOrderListDataList", RecentOrderListDataList)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.my_bottom_nav)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, HomeFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, SearchFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.recentFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, RecentFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.orderlistFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, OrderlistFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, MypageFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }
            true
        }


    }


}


