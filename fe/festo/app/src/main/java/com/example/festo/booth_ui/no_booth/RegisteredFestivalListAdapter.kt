package com.example.festo.booth_ui.no_booth

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.data.res.MyBoothListRes

@GlideModule
class RegisteredFestivalListAdapter(private var list: List<MyBoothListRes>) :
    RecyclerView.Adapter<RegisteredFestivalListAdapter.ViewHolder>(), Filterable {

    var mDataListAll = ArrayList<MyBoothListRes>(list)
    var mAccount: MutableList<MyBoothListRes> = list as MutableList<MyBoothListRes>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var imageUrl: ImageView = itemView.findViewById(R.id.festivalImage)
        var name: TextView = itemView.findViewById(R.id.festivalName)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: MyBoothListRes, position: Int) {
            Glide.with(itemView.context)
                .load(data.festivalImageUrl)
                .into(imageUrl)
            name.text = data.festivalName
        }

        @SuppressLint("CommitPrefEdits")
        override fun onClick(v: View) {
            val context: Context = v.context
            val data = list[adapterPosition]
            // SharedPreferences 인스턴스 생성
            val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            // 입력될 값의 타입에 맞는 Editor 써서 저장해야함
            val editor = sharedPreferences.edit()
            editor.putString("boothId", data.boothId.toString())
            editor.apply() // 또는 editor.commit() 사용 가능

            val intent = Intent(context, BoothMainActivity::class.java)
            context.startActivity(intent)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_myboothlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
    override fun getFilter(): Filter {
        TODO()
    }

}
