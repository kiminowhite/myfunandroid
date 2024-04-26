package com.knw.myfunandroid.logic.network.service

import com.knw.myfunandroid.logic.model.OfficialChaptersResponse
import com.knw.myfunandroid.logic.model.OfficialResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface OfficialService {
    @GET("/wxarticle/chapters/json")
    fun getOfficialChapters(): Call<OfficialChaptersResponse>

    @GET("/wxarticle/list/{aid}/{page}/json")
    fun getOffcialArticles(@Path("aid") aid: Int, @Path("page") page: Int): Call<OfficialResponse>
}