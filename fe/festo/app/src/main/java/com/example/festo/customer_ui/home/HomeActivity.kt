package com.example.festo.customer_ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.festo.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var  mBinding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        // 네비게이션들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(com.example.festo.R.id.my_nav_host) as NavHostFragment
        // 네이게이션 컨트롤러
        val navController = navHostFragment.navController

        //바텀 네비게이션 뷰와 네비게이션을 묶어준다
        NavigationUI.setupWithNavController(mBinding.myBottomNav, navController)


        // 지워야함
        val intent = Intent(this, FestivalActivity::class.java)

        val bt1 = findViewById<Button>(com.example.festo.R.id.btn1)
        bt1.setOnClickListener {
            startActivity(intent)
        }
    }




    /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
          menuInflater.inflate(R.menu.toolbar, menu)
          return true
      }*/
}


