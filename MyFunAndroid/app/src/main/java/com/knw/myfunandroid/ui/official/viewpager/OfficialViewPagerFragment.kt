package com.knw.myfunandroid.ui.official.viewpager

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
import com.knw.myfunandroid.ui.official.OfficialViewModel
import com.knw.myfunandroid.ui.project.ProjectViewModel
import com.knw.myfunandroid.ui.project.viewpager.ProjectViewPagerFragment

class OfficialViewPagerFragment:Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(OfficialViewModel::class.java) }
    private lateinit var officialArticleAdapter: OfficialArticleAdapter
    private lateinit var  officialArticleRecyclerView: RecyclerView

    private var isLoading = false

    private  var currentPage :Int =1 //从1开始
    companion object {
        fun newInstance(value: Int): OfficialViewPagerFragment {
            val fragment = OfficialViewPagerFragment()
            val args = Bundle()
            args.putInt("aid", value)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_offcial_view_pager, container, false)
        initView(view)
        initListener()
        return view
    }

    private fun initListener() {
        officialArticleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager
                if (layoutManager != null && layoutManager is LinearLayoutManager) {
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    // 如果最后一个可见的 item 的位置等于总 item 数量减去 1，表示已经滚动到了最后一个 item
                    if (lastVisibleItemPosition == totalItemCount - 1 && !isLoading) {
                        // 标记正在加载数据
                        isLoading = true
                        // 执行加载新数据的操作
                        Log.d("currentPage", currentPage.toString())
                        viewModel.getOfficalArticles(arguments!!.getInt("aid"), ++currentPage  )
                        {
                            // 加载完成后，重置标志
                            isLoading = false
                        }

                    }
                }
            }
        })
    }

    private fun initView(view: View) {
    officialArticleRecyclerView = view.findViewById(R.id.official_recycleview)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initOfficialArticles()
    }

    private fun initOfficialArticles() {
        val layoutManager = LinearLayoutManager(activity)
        officialArticleRecyclerView.layoutManager=layoutManager
        officialArticleAdapter = OfficialArticleAdapter(this,viewModel.officialArticleList)
        officialArticleRecyclerView.adapter=officialArticleAdapter
        val aid = arguments?.getInt("aid")

        if(!isLoading)
        {
            isLoading=true
            viewModel.getOfficalArticles( aid!!,currentPage){
                // 加载完成后，重置标志
                isLoading = false
            }
        }

        viewModel.officialArticleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val officialArticles = result.getOrNull()
            if (officialArticles != null) {
                viewModel.officialArticleList.addAll(officialArticles)
                officialArticleAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何文章", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
    })
        }
}