package com.knw.myfunandroid.logic.network

import com.knw.myfunandroid.logic.model.ProjectTreeResponse
import retrofit2.Call
import retrofit2.http.GET

interface ProjectService {
    @GET("/project/tree/json")
    fun getProjectTree():Call<ProjectTreeResponse>
}