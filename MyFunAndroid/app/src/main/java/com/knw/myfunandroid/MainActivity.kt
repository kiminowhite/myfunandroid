package com.knw.myfunandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var  home:MainPageBottomItemView
    private lateinit var  project:MainPageBottomItemView
    private lateinit var  official:MainPageBottomItemView
    private lateinit var  system:MainPageBottomItemView
    private lateinit var  profile:MainPageBottomItemView
    private lateinit var  mainFragmentContainer :FrameLayout

    private var selectedView: MainPageBottomItemView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initView()
        initClickListener()

        home.performClick()


    }

    private fun initClickListener() {
        home.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {
         onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)

            }
        })
        project.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {

                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)

            }
        })
        official.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {

                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)

            }
        })
        system.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {

                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)

            }
        })
        profile.setOnItemClickListener(object : MainPageBottomItemView.OnItemClickListener {
            override fun onItemClick(v: View, mItemIconPressResId: Int, mTextColorPress: Int) {


                onItemClickStyleChange(v, mItemIconPressResId, mTextColorPress)
                replaceFragment(ProfileFragment())

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


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }
}