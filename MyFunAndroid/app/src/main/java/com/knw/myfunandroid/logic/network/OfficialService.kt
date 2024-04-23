package com.knw.myfunandroid.logic.network

import com.knw.myfunandroid.logic.model.OfficialChaptersResponse
import retrofit2.Call
import retrofit2.http.GET

interface OfficialService {
    @GET("/wxarticle/chapters/json")
    fun getOfficialChapters(): Call<OfficialChaptersResponse>
}