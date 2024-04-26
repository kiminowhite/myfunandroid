package com.knw.myfunandroid.logic.network.service

import com.knw.myfunandroid.logic.model.ProjectResponse
import com.knw.myfunandroid.logic.model.ProjectTreeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectService {
    @GET("/project/tree/json")
    fun getProjectTree(): Call<ProjectTreeResponse>

    // @GET("/project/list/1/json?cid=294")
    @GET("/project/list/{page}/json")
    fun getProjectArticles(@Path("page") page: Int, @Query("cid") cid: Int): Call<ProjectResponse>
}