package com.knw.myfunandroid.ui.system.viewpager.navi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Article
import com.knw.myfunandroid.ui.home.article.ArticleInformationFragment

class NaviArticleAdapter(private val fragment: Fragment, private val articleList: List<Article>) :
    RecyclerView.Adapter<NaviArticleAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById<TextView>(R.id.title)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NaviArticleAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_navi_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: NaviArticleAdapter.ViewHolder, position: Int) {
        val article = articleList[position]
        holder.title.text = article.title

        holder.itemView.setOnClickListener({
            Log.d("test", article.link.toString())


            // 创建要跳转的 Fragment 实例，并将数据传递给它
            val articleInfoFragment = ArticleInformationFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("article", article)
                }
            }

            // 跳转到 Fragment
            fragment.requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, 0,
                    R.anim.slide_in_left, R.anim.slide_out_left
                )
                .add(android.R.id.content, articleInfoFragment, "ArticleInformationFragment")
                .addToBackStack(null)
                .commit()

        })

    }

}