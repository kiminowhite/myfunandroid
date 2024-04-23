package com.knw.myfunandroid.logic.model

data class OfficialChaptersResponse(
    val data: List<OfficialChapterItem>,
    val errorCode: Int,
    val errorMsg: String
)

data class OfficialChapterItem(
    val articleList: List<Any>,
    val author: String,
    val children: List<Any>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val type: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)