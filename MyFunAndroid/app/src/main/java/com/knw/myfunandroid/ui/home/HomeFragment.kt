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
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.utils.NetworkUtils
import com.knw.myfunandroid.ui.home.article.ArticleAdpter
import com.knw.myfunandroid.ui.home.article.ArticleViewModel

class HomeFragment:Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }
    private  var currentPage :Int =0
    private lateinit var  adapter: ArticleAdpter
    private lateinit var articleRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view :View = inflater.inflate(R.layout.fragment_home,container,false)
        articleRecyclerView= view.findViewById(R.id.article_recycleview)

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

        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        articleRecyclerView.layoutManager=layoutManager
        adapter= ArticleAdpter(this,viewModel.articleList)
        articleRecyclerView.adapter=adapter

        //获取第一页
        viewModel.getArticles(currentPage)
        Log.d("test",NetworkUtils.isNetworkAvailable(requireContext()).toString())

        viewModel.articleLiveData.observe(viewLifecycleOwner,Observer{ result ->
            val articles =result.getOrNull()
            if(articles!=null)
            {
                viewModel.articleList.addAll(articles)
                adapter.notifyDataSetChanged()
            }
            else {
                Toast.makeText(activity, "未能查询到任何文章", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }


        })

    }
}