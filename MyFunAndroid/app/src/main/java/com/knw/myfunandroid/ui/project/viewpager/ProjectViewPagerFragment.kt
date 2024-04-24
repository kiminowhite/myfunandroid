package com.knw.myfunandroid.ui.project.viewpager

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
import com.knw.myfunandroid.ui.project.ProjectViewModel
import kotlin.properties.Delegates


class ProjectViewPagerFragment: Fragment(){

    val viewModel by lazy { ViewModelProvider(this).get(ProjectViewModel::class.java) }

    private lateinit var  projectArticleAdapter: ProjectArticleAdapter
    private lateinit var projectArticleRecyclerView: RecyclerView

    private var isLoading = false

    private  var currentPage :Int =1 //从1开始
    companion object {
        fun newInstance(value: Int):ProjectViewPagerFragment {
            val fragment = ProjectViewPagerFragment()
            val args = Bundle()
            args.putInt("cid", value)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project_view_pager,container,false)

        initView(view)
        initListener()
        return view
    }

    private fun initListener() {
        projectArticleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        viewModel.getProjectArticles(++currentPage, arguments!!.getInt("cid"))
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
        projectArticleRecyclerView = view.findViewById(R.id.project_recycleview)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        initProjectArticles()
    }

    private fun initProjectArticles() {
        val layoutManager = LinearLayoutManager(activity)
        projectArticleRecyclerView.layoutManager = layoutManager
        projectArticleAdapter = ProjectArticleAdapter(this, viewModel.projectArticleList)
        projectArticleRecyclerView.adapter = projectArticleAdapter
        // 在此处获取传递的值cid,获取第一页
        val cid = arguments?.getInt("cid")
        Log.d("cid", cid.toString())
        if(!isLoading)
        {
            isLoading=true
            viewModel.getProjectArticles(currentPage, cid!!){
                // 加载完成后，重置标志
                isLoading = false
            }
        }


        viewModel.projectArticleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val projectArticles = result.getOrNull()
            if (projectArticles != null) {
                viewModel.projectArticleList.addAll(projectArticles)
                Log.d("testproject", viewModel.projectArticleList.toString())
                projectArticleAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何文章", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }


        })
    }
}
