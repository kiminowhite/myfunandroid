package com.knw.myfunandroid.ui.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R


class SearchAdpter(private val fragment: Fragment, private val searchList: List<String>) :
    RecyclerView.Adapter<SearchAdpter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById<TextView>(R.id.title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdpter.ViewHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchItem = searchList[position]
        holder.title.text = searchItem
    }


    override fun getItemCount(): Int = searchList.size


}