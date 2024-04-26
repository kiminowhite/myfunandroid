package com.knw.myfunandroid.ui.profile.favorities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.CollectArticle

class FavoritesViewModel : ViewModel() {
    private val pageLiveData = MutableLiveData<Int>()

    val collectArticleList = ArrayList<CollectArticle>()

    val collectArticleLiveData = pageLiveData.switchMap { page ->
        Repository.getCollectArticles(page)
    }

    fun getCollectArticles(page: Int, callback: () -> Unit) {
        pageLiveData.value = page
        callback()
    }
}