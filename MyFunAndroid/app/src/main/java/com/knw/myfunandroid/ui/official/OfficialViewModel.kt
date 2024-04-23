package com.knw.myfunandroid.ui.official


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.knw.myfunandroid.logic.Repository
import com.knw.myfunandroid.logic.model.OfficialChapterItem

class OfficialViewModel : ViewModel() {
    private val officlChaptersLiveData = MutableLiveData<Unit>()
            val officialChaptersList = ArrayList<OfficialChapterItem>()

    val officialChapterAuthorLiveData =officlChaptersLiveData.switchMap {
        Repository.getOfficialChapters()
    }
            fun getOfficialChapters()
            {
                officlChaptersLiveData.value=Unit
            }

}