package com.knw.myfunandroid.ui.system.viewpager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.NaviTreeItem
import com.knw.myfunandroid.logic.model.SystemTreeItem
import com.knw.myfunandroid.ui.system.NaviViewModel

class NaviViewPagerFragment :Fragment(){
    val viewModel by lazy { ViewModelProvider(this).get(NaviViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_test2,container,false)
        initView(view)
        initListener()
        return view
    }

    private fun initListener() {
    }

    private fun initView(view: View) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inintNaviTree()
    }

    private fun inintNaviTree() {
        viewModel.getNaviTree()
        viewModel.naviTreeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val naviTree = result.getOrNull()
            if(naviTree!=null)
            {
                viewModel.naviTreeItemList.addAll(naviTree)
                //  Log.d("tree",systemTree.toString())
                val t1 : NaviTreeItem =  viewModel.naviTreeItemList.get(0)
                val t2: NaviTreeItem =viewModel.naviTreeItemList.get(1)
                Log.d("navitree",t1.toString())
                Log.d("navitree",t2.toString())

            }else

            {
                Toast.makeText(activity, "未能查询到任何菜单", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()

            }


        })
    }
}