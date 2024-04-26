package com.knw.myfunandroid.ui.home.article


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.Article

class ArticleViewModel : ViewModel() {
    private val pageLiveData = MutableLiveData<Int>()
    private val topLiveData = MutableLiveData<Unit>()
    private val cidLiveData = MutableLiveData<Int>()

    val articleList = ArrayList<Article>()
    val artcileByCidList = ArrayList<Article>()

    val articleLiveData = pageLiveData.switchMap { page ->
        Repository.getArticles(page)
    }
    val topArticleLiveData = topLiveData.switchMap {
        Repository.getTopArticles()
    }
    val articleByCidLiveData = pageLiveData.switchMap { page ->
        Repository.getArticlesByCid(page, cidLiveData.value!!)
    }

    fun getArticles(page: Int) {
        pageLiveData.value = page
    }

    fun getTopArticles() {
        // 设置 topArticlesLiveData 的值，触发 articleLiveData 的更新
        topLiveData.value = Unit
    }

    fun getArticlesByCid(page: Int, cid: Int, callback: () -> Unit) {
        pageLiveData.value = page
        cidLiveData.value = cid
        callback()
    }

}