package com.example.festo.customer_ui.search

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.customer_ui.home.FestivalItemListAdapter
import com.example.festo.customer_ui.home.HomeFestivalList
import com.example.festo.databinding.ActivitySearchBinding
import com.mancj.materialsearchbar.MaterialSearchBar

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var listAdapter: FestivalItemListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lv = findViewById<RecyclerView>(R.id.recycler_view)
        val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
        searchBar.setSpeechMode(false)
        var FestivalItemListData: ArrayList<HomeFestivalList> = arrayListOf(
            HomeFestivalList(R.drawable.festival1, "a유등축제"),
            HomeFestivalList(R.drawable.festival2, "b광양 전통숯불구이 축제"),
            HomeFestivalList(R.drawable.festival1, "c유등축제"),
            HomeFestivalList(R.drawable.festival2, "d광양 전통숯불구이 축제"),
            HomeFestivalList(R.drawable.festival1, "e유등축제"),
            HomeFestivalList(R.drawable.festival2, "f광양 전통숯불구이 축제"),
            HomeFestivalList(R.drawable.festival1, "g유등축제"),
            HomeFestivalList(R.drawable.festival2, "h광양 전통숯불구이 축제"),
        )

        listAdapter = FestivalItemListAdapter(FestivalItemListData)
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recyclerView?.adapter = listAdapter
        // val listAdapter = FestivalItemListAdapt
        // er(FestivalItemListData)

        // 처음 검색 액티비티로 갔을 떄 리사이클러뷰 안보여주는 코드
        // lv.visibility = RecyclerView.INVISIBLE
        // lv.setAdapter(listAdapter)
        lv.adapter = listAdapter
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onButtonClicked(buttonCode: Int) {
                TODO("Not yet implemented")
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                // 검색창 클릭했을 때 보여주고 안보여주는 기능
                /*if (enabled){
                    lv.visibility = RecyclerView.VISIBLE
                } else {
                    lv.visibility = RecyclerView.INVISIBLE
                }*/
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                TODO("Not yet implemented")
            }
        })

        searchBar.addTextChangeListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listAdapter.getFilter().filter(s)
            }
        })

        /*lv.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@SearchActivity, listAdapter.getItem(position)!!.toString(), Toast.LENGTH_SHORT).show()
            }
        })*/


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