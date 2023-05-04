package com.example.festo.customer_ui.home

import android.content.Context
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

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        var image: ImageView = itemView!!.findViewById(R.id.photo_image)
        var title: TextView = itemView!!.findViewById(R.id.photo_name_text)

        fun bind(data: HomeFestivalList, position: Int) {
            image.setImageResource(data.image!!)
            title.text = data.title
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_festivallist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FestivalItemListAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.count()

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: MutableList<HomeFestivalList> = java.util.ArrayList<HomeFestivalList>()
            if (constraint.isEmpty()) {
                filteredList.addAll(mDataListAll)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim{ it <= ' '}
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