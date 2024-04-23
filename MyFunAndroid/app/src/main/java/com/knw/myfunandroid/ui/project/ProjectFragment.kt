package com.knw.myfunandroid.ui.project


import android.os.Bundle
import android.service.quicksettings.Tile
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.ProjectTreeItem
import com.knw.myfunandroid.ui.project.viewpager.ProjectViewPagerFragment



class ProjectFragment :Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(ProjectViewModel::class.java) }



   private lateinit var tabLayout :TabLayout
   private lateinit var viewPager: ViewPager2
    private lateinit var projectTreeTitle :List<String>
    private val listFragments: ArrayList<ProjectViewPagerFragment> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project,container,false)
        initView(view)


        return view
    }

    private fun initView(view: View) {
        tabLayout=view.findViewById(R.id.project_tab_layout)
        viewPager = view.findViewById(R.id.project_view_pager)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initProjectTree()
    }

    private fun initProjectTree() {
        viewModel.getProjectTree()
        viewModel.projectTreeLiveData.observe(viewLifecycleOwner,Observer{
            result->
           val projectTreeItems: List<ProjectTreeItem>? = result.getOrNull()
           // Log.d("test",projectTreeItems.toString())
            //拿到内部的title
            val projectNames: List<String> = projectTreeItems!!.map { it.name }

            projectTreeTitle = projectNames
            Log.d("test",projectTreeTitle.toString())


            //todo：这里要修改真正要创建的fragment，具体后面怎么改后面再说
            repeat(projectNames.size) {
                listFragments.add(ProjectViewPagerFragment())
            }

            viewPager.adapter= MyPagerAdapter(requireActivity(), listFragments)
            // TabLayout与ViewPager2绑定
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                //上方文本和集合对应
                tab.text = projectTreeTitle[position]
            }.attach()




        })
    }
    class MyPagerAdapter(
        fragmentActivity: FragmentActivity,
        private val listFragments: List<ProjectViewPagerFragment>
    ) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return listFragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return listFragments[position]
        }
    }
}