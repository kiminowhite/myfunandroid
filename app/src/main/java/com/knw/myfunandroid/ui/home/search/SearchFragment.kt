package com.knw.myfunandroid.ui.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.knw.myfunandroid.R

class SearchFragment : Fragment() {

    private lateinit var imgSearchBarBack: ImageView
    private lateinit var editSearchBar :EditText
    private lateinit var imgSearchBarClear: ImageView
    private lateinit var textSearchHotReload: TextView
    private lateinit var recyclerSearchHot: RecyclerView
    private lateinit var textSearchHistoryClear: TextView
    private lateinit var recyclerSearchHistory: RecyclerView

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

    private fun initListener() {
        imgSearchBarBack.setOnClickListener({
            requireActivity().supportFragmentManager.popBackStack()
        })
        imgSearchBarClear.setOnClickListener({
            editSearchBar.text.clear()

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

}