package com.knw.myfunandroid.ui.system

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.NaviTreeItem

class NaviViewModel :ViewModel() {
    private val treeLiveData = MutableLiveData<Unit>()

    val naviTreeItemList = ArrayList<NaviTreeItem>()
    val naviTreeLiveData =treeLiveData.switchMap {
        Repository.getNaviTree()
    }
    fun getNaviTree()
    {
        treeLiveData.value=Unit
    }
}