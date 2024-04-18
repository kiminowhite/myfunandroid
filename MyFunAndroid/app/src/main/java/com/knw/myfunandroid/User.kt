package com.knw.myfunandroid

data class User(
    var username: String,
    var password: String,
    var iconId: Int,
    var fullName: String,
    var score: Int,
    var favorites: List<Int>
)
