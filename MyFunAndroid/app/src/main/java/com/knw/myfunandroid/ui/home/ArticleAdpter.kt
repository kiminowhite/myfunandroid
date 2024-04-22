package com.knw.myfunandroid.ui.home

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Article

class ArticleAdpter(private val fragment: Fragment, private val articleList: List<Article>)
    :RecyclerView.Adapter<ArticleAdpter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shareUser:TextView =view.findViewById<TextView>(R.id.share_user)
        val niceDate:TextView =view.findViewById<TextView>(R.id.nice_date)
        val title :TextView = view.findViewById<TextView>(R.id.title)
        val superChapterName :TextView = view.findViewById<TextView>(R.id.super_chapter_name)
        val zan:ImageView = view.findViewById<ImageView>(R.id.zan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=articleList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article =articleList[position]
        holder.shareUser.text=article.shareUser
        holder.niceDate.text=article.niceDate
        holder.title.text=article.title
        holder.superChapterName.text=article.superChapterName

    }

}