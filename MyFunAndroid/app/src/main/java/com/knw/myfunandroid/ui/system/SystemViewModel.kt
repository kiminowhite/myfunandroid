package com.knw.myfunandroid.ui.system

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.SystemTreeItem


class SystemViewModel : ViewModel() {
    private val treeLiveData = MutableLiveData<Unit>()

    val systemTreeItemList = ArrayList<SystemTreeItem>()
    val systemTreeLiveData = treeLiveData.switchMap {
        Repository.getSystemTree()
    }

    fun getSystemTree() {
        treeLiveData.value = Unit
    }
}