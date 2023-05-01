package com.example.festo.customer_ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.festo.R

class FestListAdapter(val context: Context, val items: MutableList<FestiList>) : BaseAdapter() {
    override fun getCount(): Int = items.size

    override fun getItem(position: Int): FestiList = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.festival_list_item, null)
        val photoImage = view.findViewById<ImageView>(R.id.photo_image)
        val photoTitle = view.findViewById<TextView>(R.id.photo_name_text)

        val festiItem = items[position]

        photoImage.setImageResource(festiItem.image)
        photoTitle.text = festiItem.title

        return view
    }


}