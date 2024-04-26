package com.knw.myfunandroid.ui.project.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Article
import com.knw.myfunandroid.logic.model.ProjectArticle
import com.knw.myfunandroid.ui.home.article.ArticleInformationFragment

class ProjectArticleAdapter(
    private val fragment: Fragment,
    private val projectArticleList: List<ProjectArticle>
) : RecyclerView.Adapter<ProjectArticleAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shareUser: TextView = view.findViewById<TextView>(R.id.share_user)
        val niceDate: TextView = view.findViewById<TextView>(R.id.nice_date)
        val title: TextView = view.findViewById<TextView>(R.id.title)
        val des: TextView = view.findViewById<TextView>(R.id.des)
        val superChapterName: TextView = view.findViewById<TextView>(R.id.super_chapter_name)
        val zan: ImageView = view.findViewById<ImageView>(R.id.zan)
        val thumbnail: ImageView = view.findViewById<ImageView>(R.id.thumbnail)
    }

    override fun onBindViewHolder(holder: ProjectArticleAdapter.ViewHolder, position: Int) {
        val projectArticle = projectArticleList[position]
        if (projectArticle.shareUser != "") {
            holder.shareUser.text = projectArticle.shareUser
        } else {
            holder.shareUser.text = projectArticle.author
        }
        holder.niceDate.text = projectArticle.niceDate
        holder.title.text = projectArticle.title
        holder.superChapterName.text = projectArticle.superChapterName
        holder.des.text = projectArticle.desc
        Glide.with(holder.itemView.context)
            .load(projectArticle.envelopePic)
            .into(holder.thumbnail)

        holder.itemView.setOnClickListener({
            // 创建要跳转的 Fragment 实例，并将数据传递给它
            val articleInfoFragment = ArticleInformationFragment().apply {
                var article = getArticle(projectArticle.title, projectArticle.link)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_project_article, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = projectArticleList.size
}