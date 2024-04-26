package com.knw.myfunandroid.logic.network.service

import com.knw.myfunandroid.logic.model.CollectArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FavoriteService {
    @GET("/lg/collect/list/{page}/json")
    fun getCollectArticles(@Path("page") page: Int): Call<CollectArticleResponse>
}