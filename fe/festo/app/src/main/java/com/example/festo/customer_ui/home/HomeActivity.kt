package com.example.festo.customer_ui.home


import android.content.ClipData.Item
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.mypage.MypageFragment
import com.example.festo.customer_ui.mypage.RecentOrderListData
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.customer_ui.recent.RecentFragment
import com.example.festo.customer_ui.search.SearchFragment
import com.example.festo.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException



class HomeActivity : AppCompatActivity() {


    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHashKey()
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, HomeFragment())
            .commit()
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


        // 결제완료 후 주문내역으로 이동 한 경우
        val fragmentName = intent.getStringExtra("fragment")
        if (fragmentName == "orderListFragment") {
            val OrderlistFragment = OrderlistFragment()
            supportFragmentManager.beginTransaction().replace(R.id.layout_nav_bottom, OrderlistFragment).commit()
            // 네비게이션도 주문내역이 선택되어지도록 변경
            bottomNavigationView.setSelectedItemId(R.id.orderlistFragment)
        }


    }
    fun getHashKey(){
        var packageInfo : PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException){
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures){
            try{
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch(e: NoSuchAlgorithmException){
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }


}


