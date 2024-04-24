package com.knw.myfunandroid.logic.model


data class LoginResponse(
    val data: User,
    val errorCode: Int,
    val errorMsg: String
)


data class User(
    val admin: Boolean,
    val chapterTops: List<Any>, // 如果有具体的数据结构，可以替换为对应的数据类
    val coinCount: Int,
    val collectIds: List<Any>, // 如果有具体的数据结构，可以替换为对应的数据类
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


