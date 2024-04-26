package com.knw.myfunandroid.ui.profile.favorities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.knw.myfunandroid.App.Companion.isLogin
import com.knw.myfunandroid.R
import com.knw.myfunandroid.ui.profile.user.LoginFragment

class FavoritesFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(FavoritesViewModel::class.java) }

    private lateinit var imgFavoritePageBack: ImageView

    private lateinit var collectArticleAdapter: CollectArticleAdapter
    private lateinit var collectArticleRecyclerView: RecyclerView
    private lateinit var collectLoginTip: RelativeLayout
    private lateinit var collectEmptyTip: RelativeLayout
    private lateinit var textToLoginPage: TextView


    private var isLoading = false

    private var currentPage: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)


        val layout = view.findViewById<LinearLayout>(R.id.favorite_info_layout)
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
        return view
    }

    private fun initListener() {
        imgFavoritePageBack.setOnClickListener({
            requireActivity().supportFragmentManager.popBackStack()
        })



        collectArticleRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        viewModel.getCollectArticles(++currentPage)
                        {

                        }

                    }
                }
            }
        }

        )
        textToLoginPage.setOnClickListener({
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, 0, 0, R.anim.slide_out_down)
                .add(android.R.id.content, LoginFragment(), "LoginFragment").addToBackStack(null)
                .commit()

        })
    }

    private fun initView(view: View) {
        imgFavoritePageBack = view.findViewById(R.id.favorite_page_back)
        collectArticleRecyclerView = view.findViewById(R.id.favorite_page_recyle_view)
        collectLoginTip = view.findViewById(R.id.collect_login_tip)
        collectEmptyTip = view.findViewById(R.id.collect_empty_tip)
        textToLoginPage = view.findViewById(R.id.to_login_page)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initCollectArticles()
    }

    private fun initCollectArticles() {
        val layoutManager = LinearLayoutManager(activity)
        collectArticleRecyclerView.layoutManager = layoutManager
        collectArticleAdapter = CollectArticleAdapter(this, viewModel.collectArticleList)
        collectArticleRecyclerView.adapter = collectArticleAdapter
        //加载第一次
        if (!isLoading) {
            isLoading = true
            //清除原有列表重新获取
            viewModel.collectArticleList.clear()
            viewModel.getCollectArticles(currentPage) {

            }
        }


        viewModel.collectArticleLiveData.observe(viewLifecycleOwner, Observer { result ->
            val collectArticles = result.getOrNull()
            if (collectArticles != null) {


                collectArticleRecyclerView.visibility = View.VISIBLE
                collectLoginTip.visibility = View.GONE
                collectEmptyTip.visibility = View.GONE
                viewModel.collectArticleList.addAll(collectArticles)
                Log.d("collectArticleList", viewModel.collectArticleList.toString())
                collectArticleAdapter.notifyDataSetChanged()
                isLoading=false
                if(!viewModel.collectArticleList.isEmpty()&&collectArticles.isEmpty())
                {
                    Toast.makeText(activity, "已获取所有文章", Toast.LENGTH_SHORT).show()
                }
                if (viewModel.collectArticleList.isEmpty() && collectArticles.isEmpty()) {
                    Log.d("collectArticleList", "无收藏")
                    collectEmptyTip.visibility = View.VISIBLE
                    collectArticleRecyclerView.visibility = View.GONE
                    collectLoginTip.visibility = View.GONE

                }


            }
            else {
                collectLoginTip.visibility = View.VISIBLE
                collectArticleRecyclerView.visibility = View.GONE
                collectEmptyTip.visibility = View.GONE

                result.exceptionOrNull()?.printStackTrace()
            }


        })

    }

    fun refreshColletPage() {

        if (isLogin == true) {
            Log.d("collectArticleList", "刷新收藏页面")
            viewModel.collectArticleList.clear()
            viewModel.getCollectArticles(currentPage) {

            }
        }

    }
}