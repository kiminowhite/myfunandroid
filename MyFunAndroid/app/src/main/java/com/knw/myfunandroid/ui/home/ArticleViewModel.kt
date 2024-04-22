package com.knw.myfunandroid.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.Article

class ArticleViewModel: ViewModel() {
    private val searchLiveData = MutableLiveData<Int>()

     val articleList = ArrayList<Article>()

    val articleLiveData = searchLiveData.switchMap {
            page -> Repository.getArticles(page)
    }

    fun getArticles(page:Int)
    {
        searchLiveData.value =page
    }
}