package com.knw.myfunandroid.ui.official


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.OfficialArticle
import com.knw.myfunandroid.logic.model.OfficialChapterItem

class OfficialViewModel : ViewModel() {
    private val officlChaptersLiveData = MutableLiveData<Unit>()
            val officialChaptersList = ArrayList<OfficialChapterItem>()

    private val pageLiveData = MutableLiveData<Int>()
    private val aidLiveData = MutableLiveData<Int>()


    val officialArticleList = ArrayList<OfficialArticle>()


    val officialChapterAuthorLiveData =officlChaptersLiveData.switchMap {
        Repository.getOfficialChapters()
    }
            fun getOfficialChapters()
            {
                officlChaptersLiveData.value=Unit
            }

    val officialArticleLiveData = pageLiveData.switchMap {
        page->Repository.getOffcialArticles(aidLiveData.value!!,page)
    }

    fun getOfficalArticles(aid:Int,page:Int,callback: () -> Unit)
    {
        aidLiveData.value=aid
        pageLiveData.value=page
        callback()
    }

}