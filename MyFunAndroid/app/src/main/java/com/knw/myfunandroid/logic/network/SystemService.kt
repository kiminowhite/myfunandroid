package com.knw.myfunandroid.logic.network

import com.knw.myfunandroid.logic.model.SystemTreeResponse
import retrofit2.Call
import retrofit2.http.GET

interface SystemService {
    @GET("/tree/json")
    fun getSystemTree():Call<SystemTreeResponse>
}