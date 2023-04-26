package com.example.festo.customer_ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.festo.R
import org.w3c.dom.Text

class MenuAdapter(val context: Context, val MenuList: ArrayList<Menu>) : BaseAdapter() {
    override fun getCount(): Int {
        return MenuList.size
    }

    override fun getItem(position: Int): Any {
        return MenuList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.item_menu, null)
        var image = view.findViewById<ImageView>(R.id.menuImage)
        var name = view.findViewById<TextView>(R.id.menuName)
        var price = view.findViewById<TextView>(R.id.menuPrice)
        var check = view.findViewById<TextView>(R.id.menuCheck)
        var cnt = view.findViewById<TextView>(R.id.menuCnt)

        var menu = MenuList[position]
        //user 변수에 배열(또는 서버에서 받아온 데이터)에 담긴 profile, name, email, content 정보를 담아준다.

        image.setImageResource(menu.image)
        name.text = menu.name
        price.text = menu.price
        cnt.text = menu.cnt.toString()

        // 수량 변경 함수
        val addBtn = view.findViewById<ImageView>(R.id.menuAdd)
        addBtn.setOnClickListener{
            menu.cnt += 1
            // 데이터를 바꿀때 불러야하는 함수
            notifyDataSetChanged()
        }
        val minusBtn = view.findViewById<ImageView>(R.id.menuMinus)
        minusBtn.setOnClickListener{
            if (menu.cnt > 0) {
                menu.cnt -= 1
                // 데이터를 바꿀때 불러야하는 함수
                notifyDataSetChanged()
            }
        }

        // 체크 함수
        val checkBtn = view.findViewById<CheckBox>(R.id.menuCheck)
        checkBtn.setOnCheckedChangeListener{_, isChecked ->
            if (isChecked) {
                menu.check = true
                notifyDataSetChanged()
//                println("체크했음" + menu.check)
            }
            else {
                menu.check = false
                notifyDataSetChanged()
//                println("체크안했음" + menu.check)

            }
        }

        return view
        //연결이 완료된 뷰를 돌려준다.
    }

}