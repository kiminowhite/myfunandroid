package com.knw.myfunandroid.ui.system.viewpager.sys

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.SystemTreeItem

class SystemTreeAdapter(
    private val fragment: Fragment,
    private val systemTreeItems: List<SystemTreeItem>
) : RecyclerView.Adapter<SystemTreeAdapter.SystemTreeViewHolder>() {

    inner class SystemTreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.childRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemTreeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_system_tree, parent, false)
        return SystemTreeViewHolder(view)
    }

    override fun onBindViewHolder(holder: SystemTreeViewHolder, position: Int) {
        val systemTreeItem = systemTreeItems[position]
        holder.nameTextView.text = systemTreeItem.name
        holder.childRecyclerView.layoutManager = FlexboxLayoutManager(holder.itemView.context)
        holder.childRecyclerView.adapter = ChildAdapter(fragment, systemTreeItem.children)
    }

    override fun getItemCount(): Int {
        return systemTreeItems.size
    }
}
