package com.knw.myfunandroid.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.knw.myfunandroid.logic.model.Article
import com.knw.myfunandroid.logic.model.ArticleData
import com.knw.myfunandroid.logic.model.CollectArticle
import com.knw.myfunandroid.logic.model.ImgItem
import com.knw.myfunandroid.logic.model.NaviTreeItem
import com.knw.myfunandroid.logic.model.OfficialArticle
import com.knw.myfunandroid.logic.model.OfficialChapterItem
import com.knw.myfunandroid.logic.model.ProjectArticle
import com.knw.myfunandroid.logic.model.ProjectTreeItem
import com.knw.myfunandroid.logic.model.SystemTreeItem
import com.knw.myfunandroid.logic.network.MyFunAndroidNetwork
import kotlinx.coroutines.Dispatchers
import retrofit2.http.Path

object Repository {

    //首页
    fun getArticles(page: Int) = liveData(Dispatchers.IO)
    {
        Log.d("test", "测试")
        val result = try {
            Log.d("test", "进入try")
            val articleResponse = MyFunAndroidNetwork.getArticles(page)
            Log.d("test", "进入try下方")


            if (articleResponse.errorCode == 0) {

                val articleData = articleResponse.data
                val articles = articleData.datas
                Result.success(articles)
            } else {
                Result.failure(RuntimeException("response msg is${articleResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Article>>(e)
        }
        emit(result)
    }

    fun getArticlesByCid(page: Int, cid: Int) = liveData(Dispatchers.IO)
    {

        val result = try {

            val articleResponse = MyFunAndroidNetwork.getArticlesByCid(page, cid)


            if (articleResponse.errorCode == 0) {

                val articleData = articleResponse.data
                val articles = articleData.datas
                Result.success(articles)
            } else {
                Result.failure(RuntimeException("response msg is${articleResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Article>>(e)
        }
        emit(result)
    }

    fun getTopArticles() = liveData(Dispatchers.IO) {
        val result = try {
            val topArticleResponse = MyFunAndroidNetwork.getTopArticles()
            if (topArticleResponse.errorCode == 0) {
                val topArticles = topArticleResponse.data
                Result.success(topArticles)
            } else {
                Result.failure(RuntimeException("response msg is${topArticleResponse.errorMsg}"))
            }

        } catch (e: Exception) {
            Result.failure<List<Article>>(e)
        }
        emit(result)
    }

    //项目
    fun getProjectTree() = liveData(Dispatchers.IO) {
        val result = try {
            val projectTreeResponse = MyFunAndroidNetwork.getProjectTree()
            if (projectTreeResponse.errorCode == 0) {
                val projectTreeItems = projectTreeResponse.data
                Result.success(projectTreeItems)

            } else {
                Result.failure(RuntimeException("response msg is${projectTreeResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<ProjectTreeItem>>(e)
        }
        emit(result)
    }

    fun getProjectArticles(page: Int, cid: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val projectResponse = MyFunAndroidNetwork.getProjectArticles(page, cid)
            if (projectResponse.errorCode == 0) {
                val projectArticleData = projectResponse.data
                val projectArticles = projectArticleData.datas
                Result.success(projectArticles)
            } else {
                Result.failure(RuntimeException("response msg is${projectResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<ProjectArticle>>(e)
        }
        emit(result)
    }


    //公众号
    fun getOfficialChapters() = liveData(Dispatchers.IO) {
        val result = try {
            val officialChaptersResponse = MyFunAndroidNetwork.getOfficialChapters()
            if (officialChaptersResponse.errorCode == 0) {
                val officialAuthors = officialChaptersResponse.data
                Result.success(officialAuthors)
            } else {
                Result.failure(RuntimeException("response msg is${officialChaptersResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<OfficialChapterItem>>(e)
        }
        emit(result)
    }

    fun getOffcialArticles(aid: Int, page: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val officialResponse = MyFunAndroidNetwork.getOffcialArticles(aid, page)
            if (officialResponse.errorCode == 0) {
                val officialArticleData = officialResponse.data
                val officialArticles = officialArticleData.datas
                Result.success(officialArticles)
            } else {
                Result.failure(RuntimeException("response msg is${officialResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<OfficialArticle>>(e)
        }
        emit(result)
    }

    fun getSystemTree() = liveData(Dispatchers.IO) {
        val result = try {
            val systemTreeResponse = MyFunAndroidNetwork.getSystemTree()
            if (systemTreeResponse.errorCode == 0) {
                val systemTreeItemList = systemTreeResponse.data
                Result.success(systemTreeItemList)
            } else {
                Result.failure(RuntimeException("response msg is${systemTreeResponse.errorMsg}"))
            }

        } catch (e: Exception) {
            Result.failure<List<SystemTreeItem>>(e)
        }
        emit(result)
    }

    fun getNaviTree() = liveData(Dispatchers.IO) {
        val result = try {
            val naviTreeResponse = MyFunAndroidNetwork.getNaviTree()
            if (naviTreeResponse.errorCode == 0) {
                val naviTreeItemList = naviTreeResponse.data
                Result.success(naviTreeItemList)
            } else {
                Result.failure(RuntimeException("response msg is${naviTreeResponse.errorMsg}"))
            }

        } catch (e: Exception) {
            Result.failure<List<NaviTreeItem>>(e)
        }
        emit(result)
    }

    fun getCollectArticles(page: Int) = liveData(Dispatchers.IO) {
        val result = try {
            val collectArticleResponse = MyFunAndroidNetwork.getCollectArticles(page)
            if (collectArticleResponse.errorCode == 0) {
                val collectArticleData = collectArticleResponse.data
                val collectArticles = collectArticleData.datas
                Result.success(collectArticles)
            } else {
                Result.failure(RuntimeException("response msg is${collectArticleResponse.errorMsg}"))
            }
        } catch (e: Exception) {
            Result.failure<List<CollectArticle>>(e)
        }
        emit(result)
    }

    fun getBannerImgs() = liveData(Dispatchers.IO) {
        val result = try {
            val bannerResponse = MyFunAndroidNetwork.getBannerImgs()
            if (bannerResponse.errorCode == 0) {
                val imgItems = bannerResponse.data
                Result.success(imgItems)
            } else {
                Result.failure(RuntimeException("response msg is${bannerResponse.errorMsg}"))

            }
        } catch (e: Exception) {
            Result.failure<List<ImgItem>>(e)
        }
        emit(result)

    }
}