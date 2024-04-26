package com.knw.myfunandroid.ui.home.banner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.ImgItem

class BannerViewModel : ViewModel() {

    private val bannerLiveData = MutableLiveData<Unit>()
    val bannerImgItemList = ArrayList<ImgItem>()

    val bannerImgsLiveData = bannerLiveData.switchMap {
        Repository.getBannerImgs()
    }

    fun getBannerImgs() {
        bannerLiveData.value = Unit
    }
}