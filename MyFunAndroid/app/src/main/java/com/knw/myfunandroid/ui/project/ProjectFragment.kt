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


class ProjectFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(ProjectViewModel::class.java) }


    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var projectTreeIds: List<Int>
    private lateinit var projectTreeTitles: List<String>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)
        initView(view)


        return view
    }

    private fun initView(view: View) {
        tabLayout = view.findViewById(R.id.project_tab_layout)
        viewPager = view.findViewById(R.id.project_view_pager)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initProjectTree()
    }

    private fun initProjectTree() {
        viewModel.getProjectTree()
        viewModel.projectTreeLiveData.observe(viewLifecycleOwner, Observer { result ->
            //拿到所有元素
            val projectTreeItems: List<ProjectTreeItem>? = result.getOrNull()
            // Log.d("test",projectTreeItems.toString())
            //拿到内部的title
            val projectNames: List<String> = projectTreeItems!!.map { it.name }
            projectTreeTitles = projectNames
            //拿到内部的id
            val projectIds: List<Int> = projectTreeItems!!.map { it.id }
            projectTreeIds = projectIds
            Log.d("test", projectTreeTitles.toString())
            Log.d("test", projectTreeIds.toString())



            viewPager.adapter = MyPagerAdapter(requireActivity(), projectTreeIds)
            // TabLayout与ViewPager2绑定
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                //上方文本和集合对应
                tab.text = projectTreeTitles[position]
            }.attach()


        })
    }

    class MyPagerAdapter(
        fragmentActivity: FragmentActivity,
        private val projectTreeIds: List<Int>
    ) : FragmentStateAdapter(fragmentActivity) {


        override fun getItemCount(): Int {
            return projectTreeIds.size
        }

        override fun createFragment(position: Int): Fragment {
            Log.d("position", position.toString())
            Log.d("position", projectTreeIds[position].toString())
            return ProjectViewPagerFragment.newInstance(projectTreeIds[position])
        }
    }
}


/*
* class ResultFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val diffCallback = object : DiffUtil.ItemCallback<AvatarTabData>() {
        override fun areItemsTheSame(
            oldItem: AvatarTabData, newItem: AvatarTabData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AvatarTabData, newItem: AvatarTabData
        ): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun setData(newData: List<AvatarTabData>) {
        differ.submitList(newData)
    }

    override fun getItemId(position: Int): Long {
        return differ.currentList[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return differ.currentList.any { it.hashCode().toLong() == itemId }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return ResultAvatarListFragment(
            position, differ.currentList[position]
        )
    }
}


*/