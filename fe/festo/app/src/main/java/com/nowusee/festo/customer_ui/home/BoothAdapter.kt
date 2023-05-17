package com.nowusee.festo.customer_ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.nowusee.festo.R
import com.nowusee.festo.data.API.UserAPI
import com.nowusee.festo.data.res.BoothListRes
import com.nowusee.festo.data.res.BoothWaitingRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoothAdapter(val context: FestivalActivity, val BoothList: List<BoothListRes>, private val token: String, val festivalId:String) : BaseAdapter() {
    private var retrofit = RetrofitClient.client
    override fun getCount(): Int {
        return BoothList.size
    }

    override fun getItem(position: Int): Any {
        return BoothList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_booth, null)
        val boothImg = view.findViewById<ImageView>(R.id.boothLogoImage)
        val name = view.findViewById<TextView>(R.id.boothName)
        val explanation = view.findViewById<TextView>(R.id.boothExplanation)
        val waitCount = view.findViewById<TextView>(R.id.boothWaitCount)
        var cardView = view.findViewById<CardView>(R.id.cardView)
//        var festivalId = festivalId
        val booth = BoothList[position]

        val postApi = retrofit?.create(UserAPI::class.java)
        postApi!!.getBoothWaiting(token, booth.boothId.toLong()).enqueue(object : Callback<BoothWaitingRes> {
            override fun onResponse(
                call: Call<BoothWaitingRes>,
                response: Response<BoothWaitingRes>
            ) {
                if (response.isSuccessful) {
//                    println("대기인원성공!!!!!!!!!!!!!!!!!!!")
//                    println(response.body()?.waiting)
                    waitCount.text = response.body()?.waiting.toString()
//                    Log.d(" 부스대기인원", "${response.body()}")
                } else {
//                    Log.d(" 부스대기인원", "실패111111111111")
                }
            }

            override fun onFailure(call: Call<BoothWaitingRes>, t: Throwable) {
                Log.d(" 부스대기인원", "실패222222222222222")
                t.printStackTrace()
            }
        })

        Glide.with(view.getContext())
            .load(booth.imageUrl)
            .into(boothImg)

        name.text = booth.name
        explanation.text = booth.description
        waitCount.text = booth.waiting.toString()

        // 리스트뷰 하나의 아이템 뒷 부분을 눌러도 detail 페이지로 이동
        view.setOnClickListener {
            val intent = Intent(context, BoothDetailActivity::class.java)
            intent.putExtra("boothId", booth.boothId)
            Log.d("축제아이디보낸다?",festivalId)
            intent.putExtra("festivalId", festivalId)
            context.startActivity(intent)
        }

        // 카드뷰 클릭시 detail 페이지로 이동
        cardView.setOnClickListener {
            val intent = Intent(context, BoothDetailActivity::class.java)
            Log.d("축제아이디보낸다?!!",festivalId)
            intent.putExtra("festivalId", festivalId)
            intent.putExtra("boothId", booth.boothId)
            context.startActivity(intent)
        }

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}