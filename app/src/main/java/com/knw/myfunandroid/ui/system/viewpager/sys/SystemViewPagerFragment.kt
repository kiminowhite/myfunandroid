package com.knw.myfunandroid.ui.system.viewpager.sys

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
import com.knw.myfunandroid.logic.model.SystemTreeItem

import com.knw.myfunandroid.ui.system.SystemViewModel

class SystemViewPagerFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(SystemViewModel::class.java) }

    private lateinit var systemRecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_system_view_pager, container, false)
        initView(view)
        initListener()
        return view
    }

    private fun initListener() {
    }

    private fun initView(view: View) {
        systemRecyclerView = view.findViewById(R.id.system_recycleview)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inintSystemTree()
    }

    private fun inintSystemTree() {
        viewModel.getSystemTree()
        viewModel.systemTreeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val systemTree = result.getOrNull()
            if (systemTree != null) {
                viewModel.systemTreeItemList.addAll(systemTree)
                //  Log.d("tree",systemTree.toString())
                val t1: SystemTreeItem = viewModel.systemTreeItemList.get(0)
                val t2: SystemTreeItem = viewModel.systemTreeItemList.get(1)
                Log.d("systemtree", t1.toString())
                Log.d("systemtree", t2.toString())
                systemRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                systemRecyclerView.adapter = SystemTreeAdapter(this, viewModel.systemTreeItemList)

            } else {
                Toast.makeText(activity, "未能查询到任何菜单", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()

            }


        })

    }

}