package com.example.festo.customer_ui.home

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
import com.example.festo.R
import java.io.FilterReader
import java.util.Locale

class FestivalItemListAdapter(private var list: ArrayList<HomeFestivalList>) :
    RecyclerView.Adapter<FestivalItemListAdapter.ViewHolder>(), Filterable {

    var mDataListAll = ArrayList<HomeFestivalList>(list)
    var mAccount: MutableList<HomeFestivalList> = list

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var image: ImageView = itemView.findViewById(R.id.photo_image)
        var title: TextView = itemView.findViewById(R.id.photo_name_text)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: HomeFestivalList, position: Int) {
            image.setImageResource(data.image!!)
            title.text = data.title
        }

        override fun onClick(v: View) {
            val context: Context = v.context
            val intent = Intent(context, FestivalActivity::class.java)
            // 인텐트에 필요한 데이터를 추가하는 경우에는 여기서 추가합니다.
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_festivallist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<HomeFestivalList> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(mDataListAll)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in mDataListAll) {
                    if (item.title?.lowercase(Locale.getDefault())!!.contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val result = FilterResults()
            result.values = filteredList
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            mAccount.clear()
            mAccount.addAll(results?.values as Collection<HomeFestivalList>)
            notifyDataSetChanged()
        }
    }
}
