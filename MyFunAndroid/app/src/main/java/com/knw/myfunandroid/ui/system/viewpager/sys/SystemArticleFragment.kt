package com.knw.myfunandroid.ui.system.viewpager.sys

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R
import com.knw.myfunandroid.ui.home.article.ArticleAdapter
import com.knw.myfunandroid.ui.home.article.ArticleViewModel
import com.knw.myfunandroid.ui.project.viewpager.ProjectViewPagerFragment

class SystemArticleFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(ArticleViewModel::class.java) }

    private var isLoading = false
    private var currentPage: Int = 0

    private lateinit var textSystemArticleName: TextView

    private lateinit var systemArticleAdapter: ArticleAdapter
    private lateinit var systemArticleRecycleview: RecyclerView

    companion object {
        fun newInstance(value: Int, name: String): SystemArticleFragment {
            val fragment = SystemArticleFragment()
            val args = Bundle()
            args.putInt("cid", value)
            args.putString("name", name)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_system_article, container, false)
        val layout = view.findViewById<RelativeLayout>(R.id.system_article_layout)
        ViewCompat.setOnApplyWindowInsetsListener(layout) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            val params = v.layoutParams as FrameLayout.LayoutParams
            params.bottomMargin = insets.bottom

            // Return CONSUMED if you don't want want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

        initView(view)
        initListener()
        return view;
    }

    private fun initListener() {
        systemArticleRecycleview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        viewModel.getArticlesByCid(++currentPage, arguments!!.getInt("cid"))
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

        textSystemArticleName = view.findViewById(R.id.system_article_name)
        val name = arguments?.getString("name")
        textSystemArticleName.text = name
        systemArticleRecycleview = view.findViewById(R.id.system_article_recycleview)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSystemArticles()
    }

    private fun initSystemArticles() {

        val layoutManager = LinearLayoutManager(activity)
        systemArticleRecycleview.layoutManager = layoutManager
        systemArticleAdapter = ArticleAdapter(this, viewModel.artcileByCidList)
        systemArticleRecycleview.adapter = systemArticleAdapter
        // 在此处获取传递的值cid,获取第一页
        val cid = arguments?.getInt("cid")

        Log.d("syscid", cid.toString())
        if (!isLoading) {
            isLoading = true
            viewModel.getArticlesByCid(currentPage, cid!!) {
                // 加载完成后，重置标志
                isLoading = false
            }
        }
        viewModel.articleByCidLiveData.observe(viewLifecycleOwner, Observer { result ->
            val systemArticles = result.getOrNull()
            if (systemArticles != null) {
                viewModel.artcileByCidList.addAll(systemArticles)

                systemArticleAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何文章", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }


        })
    }


}