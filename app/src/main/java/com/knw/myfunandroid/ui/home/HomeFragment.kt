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
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.ImgItem
import com.knw.myfunandroid.logic.utils.NetworkUtils
import com.knw.myfunandroid.ui.home.article.ArticleAdapter
import com.knw.myfunandroid.ui.home.article.ArticleViewModel
import com.knw.myfunandroid.ui.home.banner.BannerViewModel
import com.knw.myfunandroid.ui.home.search.SearchFragment
import com.knw.myfunandroid.ui.home.viewpager.HomeViewPagerAdapter


// todo：1、修改整个页面的滑动布局 2、轮播图动画修改，添加细节 3、搜索热词功能

class HomeFragment : Fragment() {

    val articleViewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }
    val bannerViewModel by lazy { ViewModelProvider(this).get(BannerViewModel::class.java) }
    private var currentPage: Int = 0
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var buttonSearch :FloatingActionButton

    private lateinit var viewPager: ViewPager2
    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter
    private var isLoading = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        initListener()

        return view
    }

    private fun initListener() {


//        articleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val layoutManager = recyclerView.layoutManager
//                if (layoutManager != null && layoutManager is LinearLayoutManager) {
//                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
//                    val totalItemCount = layoutManager.itemCount
//
//                    // 如果最后一个可见的 item 的位置等于总 item 数量减去 1，表示已经滚动到了最后一个 item
//                    if (lastVisibleItemPosition == totalItemCount - 1) {
//                        // 执行加载新数据的操作
//                        articleViewModel.getArticles(++currentPage) // 这里调用 ViewModel 中加载新数据的方法
//                    }
//                }
//            }
//        })
        articleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    // 到达底部，加载下一页数据
                    isLoading=true
                    articleViewModel.getArticles(++currentPage)

                }
            }
        })
        buttonSearch.setOnClickListener({

            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                .add(android.R.id.content, SearchFragment(), "SearchFragment").addToBackStack(null)
                .commit()
        })



    }

    private fun initView(view: View) {
        articleRecyclerView = view.findViewById(R.id.article_recycleview)
        viewPager = view.findViewById<ViewPager2>(R.id.home_view_pager)
        buttonSearch = view.findViewById<FloatingActionButton>(R.id.search_button)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBanner()
        initArticles()

    }


    private fun initBanner() {
        bannerViewModel.getBannerImgs()
        bannerViewModel.bannerImgsLiveData.observe(viewLifecycleOwner, Observer { result ->
            val banners = result.getOrNull()
            Log.d("banner", banners.toString())
            if (banners != null) {
                bannerViewModel.bannerImgItemList.addAll(banners)
                homeViewPagerAdapter = HomeViewPagerAdapter(this, bannerViewModel.bannerImgItemList)
                viewPager.adapter = homeViewPagerAdapter
            }

        })


        val handler = Handler()

        // 设置自动切换间隔时间
        val autoScrollInterval = 3000L

// 创建自动切换的任务
        val autoScrollTask = object : Runnable {
            override fun run() {
                // 获取当前页面的索引
                val currentItem = viewPager.currentItem

                // 计算下一页的索引（如果已经是最后一页，则回到第一页）
                val nextItem =
                    if (currentItem == viewPager.adapter?.itemCount?.minus(1)) 0 else currentItem + 1

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

    private fun initArticles() {
        val layoutManager = LinearLayoutManager(activity)
        articleRecyclerView.layoutManager = layoutManager
        articleAdapter = ArticleAdapter(this, articleViewModel.articleList)
        articleRecyclerView.adapter = articleAdapter

        //获取置顶
        articleViewModel.getTopArticles()
        //获取第一页
        articleViewModel.getArticles(currentPage)
        Log.d("test", NetworkUtils.isNetworkAvailable(requireContext()).toString())

        articleViewModel.topArticleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val topArticles = result.getOrNull()
            if (topArticles != null) {
                articleViewModel.articleList.addAll(topArticles)
                articleAdapter.notifyDataSetChanged()
              isLoading=false
            }
        })

        articleViewModel.articleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val articles = result.getOrNull()
            if (articles != null&&!articles.isEmpty()) {
                articleViewModel.articleList.addAll(articles)
                articleAdapter.notifyDataSetChanged()
                isLoading=false
            }
            else if(articles != null&&articles.isEmpty())
            {

                Toast.makeText(activity, "已获取所有文章", Toast.LENGTH_SHORT).show()
                isLoading=false

            }else {
                Toast.makeText(activity, "未能查询到任何文章", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }


        })
    }
}