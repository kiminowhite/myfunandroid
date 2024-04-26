package com.knw.myfunandroid.ui.official

import android.os.Bundle
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
import com.knw.myfunandroid.logic.model.OfficialChapterItem
import com.knw.myfunandroid.ui.official.viewpager.OfficialViewPagerFragment
import com.knw.myfunandroid.ui.project.viewpager.ProjectViewPagerFragment

class OfficialFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(this).get(OfficialViewModel::class.java) }

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var officialChapterTitle: List<String>
    private lateinit var officialChapterIds: List<Int>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_offcial, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        tabLayout = view.findViewById(R.id.offcial_tab_layout)
        viewPager = view.findViewById(R.id.offcial_view_pager)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initofficialChapters()
    }

    private fun initofficialChapters() {
        viewModel.getOfficialChapters()
        viewModel.officialChapterAuthorLiveData.observe(viewLifecycleOwner, Observer { result ->
            val officialChapterItems: List<OfficialChapterItem>? = result.getOrNull()
            val authorNames = officialChapterItems!!.map { it.name }
            officialChapterTitle = authorNames
            val authorIds = officialChapterItems!!.map { it.id }
            officialChapterIds = authorIds
            Log.d("test", officialChapterTitle.toString())





            viewPager.adapter = MyPagerAdapter(requireActivity(), officialChapterIds)
            // TabLayout与ViewPager2绑定
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                //上方文本和集合对应
                tab.text = officialChapterTitle[position]
            }.attach()


        })
    }

    class MyPagerAdapter(
        fragmentActivity: FragmentActivity,
        private val officialChapterIds: List<Int>

    ) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return officialChapterIds.size
        }

        override fun createFragment(position: Int): Fragment {

            return OfficialViewPagerFragment.newInstance(officialChapterIds[position])

        }
    }
}