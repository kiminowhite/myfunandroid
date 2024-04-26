package com.knw.myfunandroid.ui.system.viewpager.sys

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Child

class ChildAdapter(private val fragment: Fragment, private val children: List<Child>) :
    RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_system_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child = children[position]
        holder.title.text = child.name

        holder.itemView.setOnClickListener({
            Log.d("child", child.id.toString())

            //去详情页
            // ProjectViewPagerFragment.newInstance(projectTreeIds[position])
            fragment.requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, 0,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                .add(
                    android.R.id.content,
                    SystemArticleFragment.newInstance(child.id, child.name),
                    "SystemArticleFragment"
                )
                .addToBackStack(null)
                .commit()

        })
    }

    override fun getItemCount(): Int {
        return children.size
    }
}
