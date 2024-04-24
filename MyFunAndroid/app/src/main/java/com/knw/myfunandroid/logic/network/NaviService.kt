package com.knw.myfunandroid.logic.network

import com.knw.myfunandroid.logic.model.NaviTreeResponse
import retrofit2.Call
import retrofit2.http.GET

interface NaviService {
    @GET("/navi/json")
    fun getNaviTree():Call<NaviTreeResponse>

}