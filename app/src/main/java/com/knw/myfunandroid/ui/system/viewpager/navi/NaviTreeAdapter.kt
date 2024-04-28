package com.knw.myfunandroid.ui.system.viewpager.navi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.NaviTreeItem

class NaviTreeAdapter(
    private val fragment: Fragment,
    private val naviTreeItems: List<NaviTreeItem>
) : RecyclerView.Adapter<NaviTreeAdapter.NaviTreeViewHolder>() {

    inner class NaviTreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val articlesRecyclerView: RecyclerView = itemView.findViewById(R.id.articlesRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NaviTreeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_navi_tree, parent, false)
        return NaviTreeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NaviTreeViewHolder, position: Int) {
        val naviTreeItem = naviTreeItems[position]
        holder.nameTextView.text = naviTreeItem.name
        holder.articlesRecyclerView.layoutManager = FlexboxLayoutManager(holder.itemView.context)
        holder.articlesRecyclerView.adapter = NaviArticleAdapter(fragment, naviTreeItem.articles)
    }

    override fun getItemCount(): Int {
        return naviTreeItems.size
    }
}
