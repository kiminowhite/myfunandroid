package com.knw.myfunandroid.ui.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R


class SearchAdapter(private val fragment: Fragment, private val searchList: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
      private var listener :onItemClickListener ?= null //监听器

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById<TextView>(R.id.title)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchItem = searchList[position]
        holder.title.text = searchItem
        holder.itemView.setOnClickListener({
            listener?.onclickItem(searchItem)
        })
    }


    override fun getItemCount(): Int = searchList.size

    interface onItemClickListener
    {
        fun onclickItem(query:String)
    }


}