package com.knw.myfunandroid.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object MyFunAndroidNetwork {
    //service
   private val  articleService =ServiceCreator.create<ArticleService>()
    private val projectService = ServiceCreator.create<ProjectService>()
    private val officialService = ServiceCreator.create<OfficialService>()
    private val systemService=ServiceCreator.create<SystemService>()
    private val  naviService = ServiceCreator.create<NaviService>()


  suspend fun  getArticles(page:Int) = articleService.getArticles(page).await()
  suspend fun   getTopArticles() = articleService.getTopArticles().await()
    suspend fun getProjectTree()= projectService.getProjectTree().await()
    suspend fun  getProjectArticles(page: Int,cid:Int) =  projectService.getProjectArticles(page,cid).await()


    suspend fun getOfficialChapters()= officialService.getOfficialChapters().await()
    suspend fun getOffcialArticles(aid:Int,page: Int) = officialService.getOffcialArticles(aid,page).await()

    suspend fun getSystemTree()= systemService.getSystemTree().await()
    suspend fun getNaviTree()= naviService.getNaviTree().await()
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                        RuntimeException("response body is null"))
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                } })
        } }
}