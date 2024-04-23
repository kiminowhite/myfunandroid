package com.knw.myfunandroid.ui.home.article

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.knw.myfunandroid.R
import com.knw.myfunandroid.R.color.card_greyblue
import com.knw.myfunandroid.logic.model.Article

class ArticleAdpter(private val fragment: Fragment, private val articleList: List<Article>)
    :RecyclerView.Adapter<ArticleAdpter.ViewHolder>(){
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shareUser:TextView =view.findViewById<TextView>(R.id.share_user)
        val niceDate:TextView =view.findViewById<TextView>(R.id.nice_date)
        val title :TextView = view.findViewById<TextView>(R.id.title)
        val superChapterName :TextView = view.findViewById<TextView>(R.id.super_chapter_name)
        val zan:ImageView = view.findViewById<ImageView>(R.id.zan)
        val card: MaterialCardView = view.findViewById<MaterialCardView>(R.id.card)
         val topMark:ImageView = view.findViewById<ImageView>(R.id.top_mark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=articleList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article =articleList[position]
        if(article.shareUser!="")
        {
            holder.shareUser.text=article.shareUser
        }else
        {
            holder.shareUser.text=article.author
        }
        holder.niceDate.text=article.niceDate
        holder.title.text=article.title
        holder.superChapterName.text=article.superChapterName
        if(article.type == 1 )
        {
            //置顶文章 修改背景颜色,置顶图片
            val color =ContextCompat.getColor(fragment.requireContext(), R.color.card_greyblue)
            holder.card.setCardBackgroundColor(color)
            holder.topMark.visibility=View.VISIBLE

        }
     if (article.type == 0)
     {
         val color =ContextCompat.getColor(fragment.requireContext(), R.color.white)
         holder.card.setCardBackgroundColor(color)
         holder.topMark.visibility=View.GONE
     }

        holder.itemView.setOnClickListener({
            Log.d("test",article.link.toString())


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