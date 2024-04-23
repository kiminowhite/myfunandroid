package com.knw.myfunandroid.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.ImgItem
import com.knw.myfunandroid.logic.utils.NetworkUtils
import com.knw.myfunandroid.ui.home.article.ArticleAdpter
import com.knw.myfunandroid.ui.home.article.ArticleViewModel
import com.knw.myfunandroid.ui.home.viewpage.ViewPagerAdpter

class HomeFragment:Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }
    private  var currentPage :Int =0
    private lateinit var  articleAdapter: ArticleAdpter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var viewPager:ViewPager2
    private lateinit var viewPagerAdpter: ViewPagerAdpter
    val imgList = listOf(
        ImgItem(
            desc = "我们支持订阅啦~",
            id = 30,
            imagePath = "https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png",
            isVisible = 1,
            order = 2,
            title = "我们支持订阅啦~",
            type = 0,
            url = "https://www.wanandroid.com/blog/show/3352"
        ),
        ImgItem(
            desc = "",
            id = 6,
            imagePath = "https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png",
            isVisible = 1,
            order = 1,
            title = "我们新增了一个常用导航Tab~",
            type = 1,
            url = "https://www.wanandroid.com/navi"
        ),
        ImgItem(
            desc = "一起来做个App吧",
            id = 10,
            imagePath = "https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
            isVisible = 1,
            order = 1,
            title = "一起来做个App吧",
            type = 1,
            url = "https://www.wanandroid.com/blog/show/2"
        )
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view :View = inflater.inflate(R.layout.fragment_home,container,false)
        initView(view)
        initListener()

        return  view
    }

    private fun initListener() {
        articleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager
                if (layoutManager != null && layoutManager is LinearLayoutManager) {
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    // 如果最后一个可见的 item 的位置等于总 item 数量减去 1，表示已经滚动到了最后一个 item
                    if (lastVisibleItemPosition == totalItemCount - 1) {
                        // 执行加载新数据的操作
                        viewModel.getArticles(currentPage++) // 这里调用 ViewModel 中加载新数据的方法
                    }
                }
            }
        })
    }

    private fun initView(view: View) {
        articleRecyclerView = view.findViewById(R.id.article_recycleview)
        viewPager = view.findViewById<ViewPager2>(R.id.home_view_pager)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        initViewPager()
       initArticles()

    }

    private fun initViewPager() {
        //等待修改成动态
        viewPagerAdpter = ViewPagerAdpter(this,imgList)
        viewPager.adapter=viewPagerAdpter
    }

    private fun  initArticles() {
        val layoutManager = LinearLayoutManager(activity)
        articleRecyclerView.layoutManager = layoutManager
        articleAdapter = ArticleAdpter(this, viewModel.articleList)
        articleRecyclerView.adapter = articleAdapter

        //获取置顶
        viewModel.getTopArticles()
        //获取第一页
        viewModel.getArticles(currentPage)
        Log.d("test", NetworkUtils.isNetworkAvailable(requireContext()).toString())

        viewModel.topArticleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val topArticles = result.getOrNull()
            if (topArticles != null) {
                viewModel.articleList.addAll(topArticles)
                articleAdapter.notifyDataSetChanged()
            }
        })

        viewModel.articleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val articles = result.getOrNull()
            if (articles != null) {
                viewModel.articleList.addAll(articles)
                articleAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何文章", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }


        })
    }
}