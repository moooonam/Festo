package com.example.festo.customer_ui.home


import RetrofitClient
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.R
import com.example.festo.customer_ui.mypage.MypageFragment
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.customer_ui.search.SearchActivity
import com.example.festo.data.API.ApiService
import com.example.festo.data.res.TestUser
import com.example.festo.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class HomeActivity : AppCompatActivity() {

    private var retrofit = RetrofitClient.client
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
                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_nav_bottom, SearchFragment()).commit()
                    return@setOnItemSelectedListener true*/
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
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
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_nav_bottom, OrderlistFragment).commit()
            // 네비게이션도 주문내역이 선택되어지도록 변경
            bottomNavigationView.setSelectedItemId(R.id.orderlistFragment)
        }

        // 액티비티에서 알림창을 누르는 경우
        if (fragmentName == "NotificationFragment") {
            val NotificationFragment = NotificationFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_nav_bottom, NotificationFragment).commit()
        }

        // 액티비티에서 네브바를 누르는 경우 각 프래그먼트로 이동, 다른 프로필에서 일반 사용자로 돌아온 경우 마이페이지로 이동
        if (fragmentName == "HomeFragment") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_nav_bottom, HomeFragment()).commit()
            bottomNavigationView.setSelectedItemId(R.id.homeFragment)
        } else if (fragmentName == "OrderlistFragment") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_nav_bottom, OrderlistFragment()).commit()
            bottomNavigationView.setSelectedItemId(R.id.orderlistFragment)
        } else if (fragmentName == "MypageFragment") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.layout_nav_bottom, MypageFragment()).commit()
            bottomNavigationView.setSelectedItemId(R.id.mypageFragment)
        }
    }

    fun getHashKey() {
        var packageInfo: PackageInfo = PackageInfo()
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        for (signature: Signature in packageInfo.signatures) {
            try {
                var md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KEY_HASH", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KEY_HASH", "Unable to get MessageDigest. signature = " + signature, e)
            }
        }
    }

    private fun startRetrofit() {
        val postApi = retrofit?.create(ApiService::class.java)
        postApi!!.getUsers().enqueue(object : Callback<List<TestUser>> {
            override fun onResponse(call: Call<List<TestUser>>, response: Response<List<TestUser>>) {
                if (response.isSuccessful) {
//                    Log.d("테스트중", "onResponse: ${response.body()}")
                    Log.d(" 테스트", "${response.body()?.get(1)}")
                }
            }

            override fun onFailure(call: Call<List<TestUser>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

//    private fun testPost() {
//        val postApi = retrofit?.create(ApiService::class.java)
//        postApi!!.postTest(TestReq()).enqueue(object : Callback<TestRes> {
//            override fun onResponse(call: Call<TestRes>, response: Response<TestRes>) {
//                Log.d("테스트트", "${response.body()}")
//            }
//
//            override fun onFailure(call: Call<TestRes>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//    }
}


