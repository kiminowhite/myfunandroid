package com.knw.myfunandroid.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.knw.myfunandroid.logic.model.Article
import com.knw.myfunandroid.logic.model.ArticleData
import com.knw.myfunandroid.logic.network.MyFunAndroidNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Path

object Repository {

    fun getArticles( page: Int) = liveData(Dispatchers.IO)
    {
        Log.d("test","测试")
     val result = try {
         Log.d("test","进入try")
         val articleResponse = MyFunAndroidNetwork.getArticles(page)
         Log.d("test","进入try下方")


         if(articleResponse.errorCode==0)
         {

             val articleData =articleResponse.data
            val articles = articleData.articles
             Result.success(articles)
         }else
         {
             Result.failure(RuntimeException("response msg is${articleResponse.errorMsg}"))
         }
     }catch (e: Exception) {
         Result.failure<List<Article>>(e)
     }
        emit(result)
    }


}