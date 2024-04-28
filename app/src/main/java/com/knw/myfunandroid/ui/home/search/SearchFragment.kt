package com.knw.myfunandroid.ui.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.App
import com.knw.myfunandroid.R
import java.util.ArrayList
import java.util.Collections


class SearchFragment : Fragment() ,SearchAdapter.onItemClickListener{

    private lateinit var imgSearchBarBack: ImageView
    private lateinit var editSearchBar :EditText
    private lateinit var imgSearchBarClear: ImageView
    private lateinit var textSearchHotReload: TextView
    private lateinit var recyclerSearchHot: RecyclerView
    private lateinit var textSearchHistoryClear: TextView
    private lateinit var recyclerSearchHistory: RecyclerView

    private val searchHotList= listOf("面试","Studio3","动画","自定义View","性能优化 速度","gradle","Camera 相机","代码混淆 安全","逆向 加固","java","python","mysql")
    private   var  searchHistoryList :ArrayList<String> = App.searchHistoryList!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initView(view)
        initListener()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerSearchHot.layoutManager= FlexboxLayoutManager(requireContext())
        val recyclerSearchHotAdapter =SearchAdapter(this,searchHotList)
        recyclerSearchHotAdapter.setOnItemClickListener(this)
        recyclerSearchHot.adapter=recyclerSearchHotAdapter

        recyclerSearchHistory.layoutManager=FlexboxLayoutManager(requireContext())
        val recyclerSearchHistoryAdapter=SearchAdapter(this,searchHistoryList)
        recyclerSearchHistoryAdapter.setOnItemClickListener(this)
        recyclerSearchHistory.adapter=recyclerSearchHistoryAdapter



    }

    private fun initListener() {
        imgSearchBarBack.setOnClickListener({
            requireActivity().supportFragmentManager.popBackStack()
        })
        imgSearchBarClear.setOnClickListener({
            editSearchBar.text.clear()

        })
        textSearchHotReload.setOnClickListener({
            Collections.shuffle(searchHotList);
            recyclerSearchHot.adapter?.notifyDataSetChanged()
        })

        textSearchHistoryClear.setOnClickListener({
            searchHistoryList.clear()
            App.searchHistoryList?.clear()
            recyclerSearchHistory.adapter?.notifyDataSetChanged()
        })
    }

    private fun initView(view: View) {
        imgSearchBarBack = view.findViewById(R.id.search_bar_back)
        editSearchBar = view.findViewById(R.id.search_bar_edit)
        imgSearchBarClear = view.findViewById(R.id.search_bar_clear)
        textSearchHotReload = view.findViewById(R.id.search_hot_reload)
        recyclerSearchHot = view.findViewById(R.id.search_hot_recyleview)
        textSearchHistoryClear = view.findViewById(R.id.search_history_clear)
        recyclerSearchHistory = view.findViewById(R.id.search_history_recyleview)

    }

    override fun onclickItem(query: String) {
      editSearchBar.setText(query)
        if(!searchHistoryList.contains(query))
        {
            searchHistoryList.add(0,query)
            recyclerSearchHistory.adapter?.notifyDataSetChanged()

        }else
        {
            val index = searchHistoryList.indexOf(query)
            searchHistoryList.removeAt(index)
            searchHistoryList.add(0,query)
            recyclerSearchHistory.adapter?.notifyDataSetChanged()

        }
        //搜索相应文章

    }

}