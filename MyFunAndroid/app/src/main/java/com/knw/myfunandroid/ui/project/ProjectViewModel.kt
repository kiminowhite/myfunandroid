package com.knw.myfunandroid.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.ProjectArticle
import com.knw.myfunandroid.logic.model.ProjectTreeItem
import java.util.ArrayList

class ProjectViewModel:ViewModel() {

    private val treeLiveData = MutableLiveData<Unit>()
    private val pageLiveData = MutableLiveData<Int>()
    private val cidLiveData = MutableLiveData<Int>()


    val projectTreeList=ArrayList<ProjectTreeItem>()
    val projectArticleList = ArrayList<ProjectArticle>()

    val projectTreeLiveData = treeLiveData.switchMap {
        Repository.getProjectTree() }

    val projectArticleLiveData= pageLiveData.switchMap {
       page-> Repository.getProjectArticles(page,cidLiveData.value!!)
    }

    fun getProjectTree()
    {
        treeLiveData.value=Unit
    }
    fun  getProjectArticles(page:Int,cid:Int,callback: () -> Unit)
    {
        pageLiveData.value =page
        cidLiveData.value= cid
        callback()
    }
}