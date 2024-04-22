package com.knw.myfunandroid

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.knw.myfunandroid.ui.customview.MainPageBottomItemView
import com.knw.myfunandroid.ui.home.HomeFragment
import com.knw.myfunandroid.ui.official.OfficialFragment
import com.knw.myfunandroid.ui.profile.ProfileFragment
import com.knw.myfunandroid.ui.project.ProjectFragment
import com.knw.myfunandroid.ui.system.SystemFragment

class MainActivity : AppCompatActivity() {
    private lateinit var  home: MainPageBottomItemView
    private lateinit var  project: MainPageBottomItemView
    private lateinit var  official: MainPageBottomItemView
    private lateinit var  system: MainPageBottomItemView
    private lateinit var  profile: MainPageBottomItemView
    private lateinit var  mainFragmentContainer :FrameLayout

    private var selectedView: MainPageBottomItemView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val layout:RelativeLayout= findViewById(R.id.main)

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

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT



        initView()
        initClickListener()

        //默认进入首页
        home.performClick()


    }

    private fun initClickListener() {
        home.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {
         onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)
                replaceFragment(HomeFragment(),"HomeFragment")

            }
        })
        project.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {

                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)
                replaceFragment(ProjectFragment(),"ProjectFragment")

            }
        })
        official.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {

                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)
                replaceFragment(OfficialFragment(),"OfficialFragment")

            }
        })
        system.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {

                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)
                replaceFragment(SystemFragment(),"SystemFragment")

            }
        })
        profile.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {


                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)
                replaceFragment(ProfileFragment(),"ProfileFragment")

            }
        })
    }

    private fun  initView() {
        home = findViewById<MainPageBottomItemView>(R.id.home)
        project = findViewById<MainPageBottomItemView>(R.id.project)
        official = findViewById<MainPageBottomItemView>(R.id.official_accounts)
        system = findViewById<MainPageBottomItemView>(R.id.system)
        profile = findViewById<MainPageBottomItemView>(R.id.profile)
        mainFragmentContainer= findViewById<FrameLayout>(R.id.main_fragment_container);


    }

    private fun onItemClickStyleChange(
        v: View,
        mItemIconPressResId: Int,
        mTextColorPress: Int
    ) {
        selectedView?.resetStyle()
        val mItemIcon = v.findViewById<ImageView>(R.id.main_page_botton_item_icon)
        val mItemName = v.findViewById<TextView>(R.id.main_page_botton_item_name)
        mItemIcon.setImageResource(mItemIconPressResId)
        mItemName.setTextColor(mTextColorPress)
        selectedView = v as? MainPageBottomItemView
    }


    private fun replaceFragment(fragment: Fragment,tag:String) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment,tag)
        transaction.commit()
    }
}