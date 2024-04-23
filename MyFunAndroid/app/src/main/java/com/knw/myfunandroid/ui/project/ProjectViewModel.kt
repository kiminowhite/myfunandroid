package com.knw.myfunandroid.ui.project

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.ProjectTreeItem
import java.util.ArrayList

class ProjectViewModel:ViewModel() {

    private val treeLiveData = MutableLiveData<Unit>()
    val projectTreeList=ArrayList<ProjectTreeItem>()

    val projectTreeLiveData = treeLiveData.switchMap {
        Repository.getProjectTree() }

    fun getProjectTree()
    {
        treeLiveData.value=Unit
    }
}