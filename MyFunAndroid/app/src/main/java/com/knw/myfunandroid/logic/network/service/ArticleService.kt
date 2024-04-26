package com.knw.myfunandroid.logic.network.service

import com.knw.myfunandroid.logic.model.ArticleResponse
import com.knw.myfunandroid.logic.model.TopArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {

    @GET("/article/list/{page}/json")
    fun getArticles(@Path("page") page: Int): Call<ArticleResponse>

    @GET("/article/list/{page}/json")
    fun getArticlesByCid(@Path("page") page: Int, @Query("cid") cid: Int): Call<ArticleResponse>

    @GET("/article/top/json")
    fun getTopArticles(): Call<TopArticleResponse>

}