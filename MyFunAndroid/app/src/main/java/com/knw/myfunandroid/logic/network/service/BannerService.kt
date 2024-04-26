package com.knw.myfunandroid.logic.network.service


import com.knw.myfunandroid.logic.model.BannerResponse
import retrofit2.Call
import retrofit2.http.GET

interface BannerService {
    @GET("/banner/json")
    fun getBannerImgs(): Call<BannerResponse>
}