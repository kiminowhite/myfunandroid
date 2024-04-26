package com.knw.myfunandroid.logic.network.service


import com.knw.myfunandroid.logic.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @FormUrlEncoded
    @POST("/user/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("/user/register")
    fun userSignUp(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    )
            : Call<LoginResponse>
}