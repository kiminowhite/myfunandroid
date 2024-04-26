package com.knw.myfunandroid.ui.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.knw.myfunandroid.R
import com.knw.myfunandroid.ui.system.viewpager.navi.NaviViewPagerFragment
import com.knw.myfunandroid.ui.system.viewpager.sys.SystemViewPagerFragment


class SystemFragment : Fragment() {
    private val listFragments: ArrayList<Fragment> = ArrayList()
    private val title = arrayOf("体系", "导航")
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_system, container, false)
        initView(view)

        listFragments.add(SystemViewPagerFragment())
        listFragments.add(NaviViewPagerFragment())
        viewPager.adapter = MyPagerAdapter(requireActivity(), listFragments)

        // TabLayout与ViewPager2绑定
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //上方文本和集合对应
            tab.text = title[position]
        }.attach()

        // 监听选中的下标
        // tabLayout.addOnTabSelectedListener()


        return view
    }

    private fun initView(view: View) {
        tabLayout = view.findViewById(R.id.system_tab_layout)
        viewPager = view.findViewById(R.id.system_view_pager)

    }


    class MyPagerAdapter(
        fragmentActivity: FragmentActivity,
        private val listFragments: List<Fragment>
    ) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return listFragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return listFragments[position]
        }
    }

}