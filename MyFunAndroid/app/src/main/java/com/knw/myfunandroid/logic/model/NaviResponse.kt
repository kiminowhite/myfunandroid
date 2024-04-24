package com.knw.myfunandroid.logic.model

data class NaviTreeResponse(
    val data : List<NaviTreeItem>,
    val errorCode: Int,
    val errorMsg: String
)

data class NaviTreeItem(
    val articles: List<Article>,
    val cid: Int,
    val name: String
)