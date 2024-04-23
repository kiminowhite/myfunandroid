package com.knw.myfunandroid.ui.home

import android.os.Bundle
import android.os.Handler
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
import com.knw.myfunandroid.ui.home.article.ArticleAdapter
import com.knw.myfunandroid.ui.home.article.ArticleViewModel
import com.knw.myfunandroid.ui.home.viewpager.HomeViewPagerAdapter


// todo：1、修改整个页面的滑动布局 2、轮播图动画修改，添加细节，数据从网络获取 3、搜索热词功能

class HomeFragment:Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }
    private  var currentPage :Int =0
    private lateinit var  articleAdapter: ArticleAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var viewPager:ViewPager2
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
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
        //todo:等待修改成动态数据
        homeViewPagerAdapter = HomeViewPagerAdapter(this,imgList)
        viewPager.adapter=homeViewPagerAdapter

        val handler = Handler()

   // 设置自动切换间隔时间
        val autoScrollInterval = 3000L

// 创建自动切换的任务
        val autoScrollTask = object : Runnable {
            override fun run() {
                // 获取当前页面的索引
                val currentItem = viewPager.currentItem

                // 计算下一页的索引（如果已经是最后一页，则回到第一页）
                val nextItem = if (currentItem == viewPager.adapter?.itemCount?.minus(1)) 0 else currentItem + 1

                // 切换到下一页
                viewPager.setCurrentItem(nextItem, true)
            }
        }

// 监听用户滑动操作
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        // 当用户开始拖动页面时停止自动切换
                        handler.removeCallbacks(autoScrollTask)
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        // 当页面停止滚动时重新启动自动切换
                        handler.postDelayed(autoScrollTask, autoScrollInterval)
                    }
                }
            }
        })

// 启动自动切换任务
        handler.postDelayed(autoScrollTask, autoScrollInterval)





    }

    private fun  initArticles() {
        val layoutManager = LinearLayoutManager(activity)
        articleRecyclerView.layoutManager = layoutManager
        articleAdapter = ArticleAdapter(this, viewModel.articleList)
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