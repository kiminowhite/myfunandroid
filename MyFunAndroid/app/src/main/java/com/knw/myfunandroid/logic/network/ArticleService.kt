package com.knw.myfunandroid.logic.network

import com.knw.myfunandroid.logic.model.ArticleResponse
import com.knw.myfunandroid.logic.model.TopArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleService {

    @GET("/article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Call<ArticleResponse>

    @GET("/article/top/json")
    fun getTopArticles():Call<TopArticleResponse>

}