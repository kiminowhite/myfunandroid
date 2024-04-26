package com.knw.myfunandroid.ui.profile.favorities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Article
import com.knw.myfunandroid.logic.model.CollectArticle
import com.knw.myfunandroid.ui.home.article.ArticleInformationFragment

class CollectArticleAdapter(
    private val fragment: Fragment,
    private val collectArticleList: List<CollectArticle>
) : RecyclerView.Adapter<CollectArticleAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val author: TextView = view.findViewById<TextView>(R.id.author)
        val niceDate: TextView = view.findViewById<TextView>(R.id.nice_date)
        val title: TextView = view.findViewById<TextView>(R.id.title)
        val chapterName: TextView = view.findViewById<TextView>(R.id.chapter_name)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collect_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val collectArticle = collectArticleList[position]
        holder.author.text = collectArticle.author

        holder.niceDate.text = collectArticle.niceDate
        holder.title.text = collectArticle.title
        holder.chapterName.text = collectArticle.chapterName


        holder.itemView.setOnClickListener({
            // 创建要跳转的 Fragment 实例，并将数据传递给它
            val articleInfoFragment = ArticleInformationFragment().apply {
                var article = getArticle(collectArticle.title, collectArticle.link)
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

    override fun getItemCount(): Int = collectArticleList.size


    private fun getArticle(title: String, link: String): Article {
        return Article(
            adminAdd = false,
            apkLink = "",
            audit = 0,
            author = "",
            canEdit = false,
            chapterId = 0,
            chapterName = "",
            collect = false,
            courseId = 0,
            desc = "",
            descMd = "",
            envelopePic = "",
            fresh = false,
            host = "",
            id = 0,
            isAdminAdd = false,
            link = link,
            niceDate = "",
            niceShareDate = "",
            origin = "",
            prefix = "",
            projectLink = "",
            publishTime = 0L,
            realSuperChapterId = 0,
            selfVisible = 0,
            shareDate = 0L,
            shareUser = "",
            superChapterId = 0,
            superChapterName = "",
            tags = emptyList(),
            title = title,
            type = 0,
            userId = 0,
            visible = 0,
            zan = 0
        )


    }
}
