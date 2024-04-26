package com.knw.myfunandroid.ui.home.viewpager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Article
import com.knw.myfunandroid.logic.model.ImgItem
import com.knw.myfunandroid.ui.home.article.ArticleInformationFragment

class HomeViewPagerAdapter(private val fragment: Fragment, private val imgsList: List<ImgItem>) :
    RecyclerView.Adapter<HomeViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgViewPagerItem: ImageView = view.findViewById<ImageView>(R.id.view_page_item_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_page, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = imgsList.size


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img = imgsList[position]
        Glide.with(holder.itemView.context).load(img.imagePath).into(holder.imgViewPagerItem)

        holder.itemView.setOnClickListener({
            // 创建要跳转的 Fragment 实例，并将数据传递给它
            val articleInfoFragment = ArticleInformationFragment().apply {
                arguments = Bundle().apply {
                    val targetArticle = getTargetArticle(img.title, img.url)
                    putSerializable("article", targetArticle)
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

    private fun getTargetArticle(title: String, url: String): Article {
        val article = Article(
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
            link = url,
            niceDate = "",
            niceShareDate = "",
            origin = "",
            prefix = "",
            projectLink = "",
            publishTime = 0,
            realSuperChapterId = 0,
            selfVisible = 0,
            shareDate = 0,
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
        return article
    }

}