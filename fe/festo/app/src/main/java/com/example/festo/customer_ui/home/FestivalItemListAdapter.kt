package com.example.festo.customer_ui.home

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
import com.example.festo.data.res.FestivalListRes
import java.util.Locale

@GlideModule
class FestivalItemListAdapter(private var list: List<FestivalListRes>) :
    RecyclerView.Adapter<FestivalItemListAdapter.ViewHolder>(), Filterable {

    var mDataListAll = ArrayList<FestivalListRes>(list)
    // var mAccount: MutableList<FestivalListRes> = list as MutableList<FestivalListRes>
    var mAccount: MutableList<FestivalListRes> = ArrayList(list)


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var imageUrl: ImageView = itemView.findViewById(R.id.photo_image)
        var name: TextView = itemView.findViewById(R.id.photo_name_text)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(data: FestivalListRes, position: Int) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(imageUrl)
            name.text = data.name
        }

        override fun onClick(v: View) {
            val context: Context = v.context
            val intent = Intent(context, FestivalActivity::class.java)
            val data = list[adapterPosition]
            intent.putExtra("festivalId", data.festivalId.toString())
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
            val filteredList: MutableList<FestivalListRes> = ArrayList()
            if (constraint.isEmpty()) {
                filteredList.addAll(mDataListAll)
            } else {
                val filterPattern = constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in mDataListAll) {
                    if (item.name?.lowercase(Locale.getDefault())!!.contains(filterPattern)) {
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
            mAccount.addAll(results?.values as Collection<FestivalListRes>)
            notifyDataSetChanged()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<FestivalListRes>) {
        list = newList
        mDataListAll = ArrayList(newList)
        mAccount = ArrayList(newList)
        notifyDataSetChanged()
    }
}
