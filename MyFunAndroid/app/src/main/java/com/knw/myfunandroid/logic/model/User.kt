package com.knw.myfunandroid.logic.model


data class LoginResponse(
    val data: User,
    val errorCode: Int,
    val errorMsg: String
)


data class User(
    val admin: Boolean,
    val chapterTops: List<Any>,
    val coinCount: Int,
    val collectIds: List<Int>,
    val email: String,
    val icon: String,
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)


