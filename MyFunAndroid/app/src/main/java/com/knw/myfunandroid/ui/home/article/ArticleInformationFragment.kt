package com.knw.myfunandroid.ui.home.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.Article

class ArticleInformationFragment :Fragment() {
    private lateinit var  imgArticleInfoBack:ImageView
    private lateinit var textArticleInfoTitle:TextView
    private lateinit var imgArticleInfoNet :ImageView
    private lateinit var imgArticleInfoMore:ImageView
    private lateinit var webViewArticleInfoDetails:WebView
    private lateinit var imgArticleInfoDetailsBack:ImageView
    private lateinit var imgArticleInfoDetailsForward :ImageView
    private lateinit var imgArticleInfoDetailsRefresh :ImageView
    private lateinit var imgArticleInfoDetailsZan:ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view :View  = inflater.inflate(R.layout.fragment_article_info,container,false)
        // 获取传递的 Article 对象
        val  article = arguments?.getSerializable("article") as? Article

        val layout =  view.findViewById<LinearLayout>(R.id.article_info_layout)
        ViewCompat.setOnApplyWindowInsetsListener(layout) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. This solution sets
            // only the bottom, left, and right dimensions, but you can apply whichever
            // insets are appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            val params =  v.layoutParams as FrameLayout.LayoutParams
            params.bottomMargin =insets.bottom

            // Return CONSUMED if you don't want want the window insets to keep passing
            // down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
        initView(view,article)
        initListener(article)
        return view
    }

    private fun initListener(article: Article?) {
        imgArticleInfoBack.setOnClickListener({
            requireActivity().supportFragmentManager.popBackStack()
        })
        imgArticleInfoNet.setOnClickListener({
            val url = article?.link
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("使用浏览器访问该网页")
                setMessage("您即将离开本应用并使用本机默认浏览器打开网页，是否打开网页？")
                setPositiveButton("确定") { dialog, which ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
                setNegativeButton("取消") { dialog, which ->
                    // 用户点击取消按钮时执行的操作（可选）
                }
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        })
        imgArticleInfoMore.setOnClickListener({
            val popupMenu = PopupMenu(requireContext(), imgArticleInfoMore)
            popupMenu.getMenuInflater().inflate(R.menu.share, popupMenu.getMenu())
            popupMenu.show()

        })
        imgArticleInfoDetailsRefresh.setOnClickListener({
            webViewArticleInfoDetails.settings.javaScriptEnabled = true
            article?.link?.let { webViewArticleInfoDetails.loadUrl(it) }
        })
    }

    private fun initView(view: View, article: Article?) {
        imgArticleInfoBack = view.findViewById<ImageView>(R.id.article_info_back)

        textArticleInfoTitle = view.findViewById<TextView>(R.id.article_info_titile)
        textArticleInfoTitle.text=article?.title

        imgArticleInfoNet =view.findViewById<ImageView>(R.id.article_info_net)

        imgArticleInfoMore = view.findViewById<ImageView>(R.id.article_info_more)

        webViewArticleInfoDetails = view.findViewById<WebView>(R.id.article_info_details_webview)
        webViewArticleInfoDetails.settings.domStorageEnabled = true
        webViewArticleInfoDetails.settings.javaScriptEnabled = true
        webViewArticleInfoDetails.settings.setBlockNetworkImage(false)
        article?.link?.let { webViewArticleInfoDetails.loadUrl(it) }

        imgArticleInfoDetailsBack = view.findViewById<ImageView>(R.id.article_info_details_back)

        imgArticleInfoDetailsForward = view.findViewById<ImageView>(R.id.article_info_details_forward)

        imgArticleInfoDetailsRefresh = view.findViewById<ImageView>(R.id.article_info_details_refresh)

        imgArticleInfoDetailsZan = view.findViewById<ImageView>(R.id.article_info_details_zan)

    }
}