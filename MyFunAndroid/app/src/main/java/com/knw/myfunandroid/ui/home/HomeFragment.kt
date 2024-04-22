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
import com.knw.myfunandroid.logic.Repository
import kotlin.concurrent.thread

class HomeFragment:Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }
    private lateinit var  adapter: ArticleAdpter
    private lateinit var articleRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view :View = inflater.inflate(R.layout.fragment_home,container,false)
        articleRecyclerView= view.findViewById(R.id.article_recycleview)
        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        articleRecyclerView.layoutManager=layoutManager
        adapter= ArticleAdpter(this,viewModel.articleList)
        articleRecyclerView.adapter=adapter

        viewModel.getArticles(0)
        viewModel.articleLiveData.observe(viewLifecycleOwner,Observer{ result ->
            val articles =result.getOrNull()
            if(articles!=null)
            {
                viewModel.articleList.clear()
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