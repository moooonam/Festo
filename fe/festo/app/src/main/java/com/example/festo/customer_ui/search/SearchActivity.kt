package com.example.festo.customer_ui.search

import RetrofitClient
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.customer_ui.home.FestivalItemListAdapter
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.FestivalListRes
import com.example.festo.databinding.ActivitySearchBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mancj.materialsearchbar.MaterialSearchBar
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private var retrofit = RetrofitClient.client
    private var searchList = emptyList<FestivalListRes>()

    private lateinit var binding: ActivitySearchBinding
    private lateinit var listAdapter: FestivalItemListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 네비게이션
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.my_bottom_nav)
        // 검색 액티비티 들어오면 네비게시션도 검색에 고정
        bottomNavigationView.setSelectedItemId(R.id.searchFragment)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "HomeFragment")
                    startActivity(intent)
                }

                R.id.searchFragment -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                }


                R.id.orderlistFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "OrderlistFragment")
                    startActivity(intent)
                }

                R.id.mypageFragment -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("fragment", "MypageFragment")
                    startActivity(intent)
                }
            }
            true
        }

        val api = retrofit?.create(UserAPI::class.java)
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        api!!.getFestivalList(token).enqueue(object : retrofit2.Callback<List<FestivalListRes>> {
            override fun onResponse(
                call: Call<List<FestivalListRes>>,
                response: Response<List<FestivalListRes>>
            ) {
                if (response.isSuccessful) {
                    Log.i("서치 성공", "${response.body()}")
                    searchList = response.body()?: emptyList()
                    listAdapter = FestivalItemListAdapter(searchList)
                    binding?.recyclerView?.layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
                    binding?.recyclerView?.adapter = listAdapter

                    val reView = findViewById<RecyclerView>(R.id.recycler_view)
                    val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
                    // 음성 off
                    searchBar.setSpeechMode(false)
                    // 처음 검색 액티비티로 갔을 떄 리사이클러뷰 안보여주는 코드
                    // reView.visibility = RecyclerView.INVISIBLE
                    // reView.setAdapter(listAdapter)
                    reView.adapter = listAdapter
                    searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
                        override fun onButtonClicked(buttonCode: Int) {
                            TODO("Not yet implemented")
                        }
                        override fun onSearchStateChanged(enabled: Boolean) {
                            // 검색창 클릭했을 때 보여주고 안보여주는 기능
                            if (enabled){
                                reView.visibility = RecyclerView.VISIBLE
                            } else {
                                reView.visibility = RecyclerView.INVISIBLE
                            }
                        }
                        override fun onSearchConfirmed(text: CharSequence?) {
                            TODO("Not yet implemented")
                        }
                    })
                    searchBar.addTextChangeListener(object : TextWatcher{
                        override fun afterTextChanged(s: Editable?) {
                            listAdapter.updateList(searchList.filter {
                                it.name!!.contains(s.toString())
                            })
                        }
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        }
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            listAdapter.getFilter().filter(s)
                        }
                    })

                }
            }
            override fun onFailure(call: Call<List<FestivalListRes>>, t: Throwable) {
                Log.i("서치 실패", "$t")
            }
        })

        // 알림으로 이동
        val notificationBtn = findViewById<ImageView>(R.id.notification_btn)
        notificationBtn.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("fragment", "NotificationFragment")
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }
}