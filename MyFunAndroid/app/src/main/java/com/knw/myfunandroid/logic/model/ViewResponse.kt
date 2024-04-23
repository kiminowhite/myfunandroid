package com.knw.myfunandroid.logic.model

data class ViewResponse(
    val data: List<ImgItem>,
    val errorCode: Int,
    val errorMsg: String
)

data class ImgItem(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)