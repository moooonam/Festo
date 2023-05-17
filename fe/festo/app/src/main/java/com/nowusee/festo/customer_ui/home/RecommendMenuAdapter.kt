package com.nowusee.festo.customer_ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nowusee.festo.R
import com.nowusee.festo.data.res.MenuRecommendRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale

class RecommendMenuAdapter(val context: Context, var RecommendMenuList: List<MenuRecommendRes>, val myOrderList: ArrayList<MyOrderList>, var boothId:String, var festivalId:String) : BaseAdapter() {
    override fun getCount(): Int {
        return RecommendMenuList.size
    }

    override fun getItem(position: Int): Any {
        return RecommendMenuList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    // 추천 메뉴를 myorderlist에 추가
    fun addMenuToOrderList(position: Int, count: Int) {
        val recommendMenu = RecommendMenuList[position]
        val myOrder = MyOrderList(recommendMenu.product_id.toString(), recommendMenu.image_url, recommendMenu.name, recommendMenu.price, count)
        myOrderList.add(myOrder)
        notifyDataSetChanged()
    }

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_recommend_menu, null)
        val image = view.findViewById<ImageView>(R.id.menuImage)
        val name = view.findViewById<TextView>(R.id.menuName)
        val price = view.findViewById<TextView>(R.id.menuPrice)

        val recommendMenu = RecommendMenuList[position]
        Log.d("추천된메뉴뉴뉴뉴뉴뉸", recommendMenu.toString())
        // 데이터 연결. 이미지는 아직 안넣어줬음
        name.text = recommendMenu.name
        val formatter: NumberFormat = NumberFormat.getNumberInstance(Locale.KOREA)
        val formattedString = formatter.format(recommendMenu.price)
        price.text = "${formattedString}원"
        Glide.with(view.getContext())
            .load(recommendMenu.image_url)
            .into(image)

        // 추천메뉴 담기 클릭
        val addBtn = view.findViewById<TextView>(R.id.addBtn)
        val countView : View = LayoutInflater.from(context).inflate(R.layout.dialog_recommend_menu, null)
        addBtn.setOnClickListener {
            val context = countView.context
            val minusButton = countView.findViewById<Button>(R.id.minusBtn)
            val plusButton = countView.findViewById<Button>(R.id.addBtn)
            val menuCnt = countView.findViewById<TextView>(R.id.menuCnt)

            var count = 0

            minusButton.setOnClickListener {
                if (count > 0) {
                    count--
                    menuCnt.text = count.toString()
                }
            }

            plusButton.setOnClickListener {
                count++
                menuCnt.text = count.toString()
            }
            MaterialAlertDialogBuilder(context)
                .setTitle("이 메뉴를 추가 하시겠어요?")
                .setMessage("수량을 확인해주세요.")
                .setView(countView) // 레이아웃 추가
                .setNeutralButton("취소") { dialog, which ->
                    // Respond to neutral button press
                }
                .setNegativeButton("확인") { dialog, which ->
                    // Respond to negative button press
                    addMenuToOrderList(position, count)
                    notifyDataSetChanged()
                    val intent = Intent(context, PaymentActivity::class.java)
                    intent.putExtra("myOrderList", myOrderList)
                    intent.putExtra("boothId", boothId)
                    intent.putExtra("festivalId",festivalId)
                    context.startActivity(intent)
                }
                .show()
        }

        return view
        //연결이 완료된 뷰를 돌려준다.
    }
    fun updateList(newList: List<MenuRecommendRes>) {
        RecommendMenuList = newList
        notifyDataSetChanged()
    }

}