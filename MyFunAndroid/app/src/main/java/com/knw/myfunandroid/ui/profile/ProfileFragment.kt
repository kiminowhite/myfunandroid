package com.knw.myfunandroid.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.knw.myfunandroid.App.Companion.cookies
import com.knw.myfunandroid.App.Companion.isLogin
import com.knw.myfunandroid.App.Companion.loginUser
import com.knw.myfunandroid.R
import com.knw.myfunandroid.ui.profile.favorities.FavoritesFragment
import com.knw.myfunandroid.ui.profile.user.LoginFragment
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {

    private lateinit var layoutProFileLogin: RelativeLayout //登陆成功布局
    private lateinit var layoutProfileNotLogin: RelativeLayout //未登录布局
    private lateinit var textLogin: TextView //点击去登陆
    private lateinit var viewLoginIcon: CircleImageView //登陆头像
    private lateinit var textLoginUsername: TextView //登陆用户名称
    private lateinit var textLoginUserScore: TextView //登陆用户积分
    private lateinit var imgLogout: TextView //退出登陆
    private lateinit var layoutProfilFavorites: RelativeLayout //去收藏页面
    private lateinit var layoutProfileUpdate: RelativeLayout //去更新页面
    private lateinit var layoutProfileSettings: RelativeLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        initView(view)
        initListner()

        return view
    }

    private fun initListner() {
        textLogin.setOnClickListener(View.OnClickListener {
            //跳转登陆fragment


            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, 0, 0, R.anim.slide_out_down)
                .add(android.R.id.content, LoginFragment(), "LoginFragment").addToBackStack(null)
                .commit()


        })
        imgLogout.setOnClickListener(View.OnClickListener {
            isLogin = false
            loginUser = null
            cookies = null
            refreshLoginState()
            Toast.makeText(context, "您已顺利退出登陆", Toast.LENGTH_SHORT).show()
        })
        layoutProfilFavorites.setOnClickListener({

            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, 0,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                .add(android.R.id.content, FavoritesFragment(), "FavoritesFragment")
                .addToBackStack(null)
                .commit()

        })
        layoutProfileSettings.setOnClickListener({
            Toast.makeText(requireContext(), "前面的区域，以后再来探索吧", Toast.LENGTH_SHORT).show()
        })
        layoutProfileUpdate.setOnClickListener({
            Toast.makeText(requireContext(), "前面的区域，以后再来探索吧", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initView(view: View) {
        layoutProFileLogin = view.findViewById<RelativeLayout>(R.id.profile_login_layout)
        layoutProfileNotLogin = view.findViewById<RelativeLayout>(R.id.profile_not_login_layout)
        layoutProFileLogin.findViewById<CircleImageView>(R.id.login_icon)
        textLogin = view.findViewById<TextView>(R.id.login)
        viewLoginIcon = view.findViewById<CircleImageView>(R.id.login_icon)
        textLoginUsername = view.findViewById<TextView>(R.id.login_username)
        textLoginUserScore = view.findViewById<TextView>(R.id.user_socore)
        imgLogout = view.findViewById<TextView>(R.id.logout)
        layoutProfilFavorites = view.findViewById<RelativeLayout>(R.id.profile_favorites)
        layoutProfileUpdate = view.findViewById<RelativeLayout>(R.id.profile_update)
        layoutProfileSettings = view.findViewById<RelativeLayout>(R.id.profile_settings)


        //进入页面后判断是否登陆，选择显示内容，后期需要做登陆判断修改isLogin
        //这里要写登陆判断的逻辑,默认未登录
        refreshLoginState()

    }

    /*
      刷新login和非login状态
     */
    fun refreshLoginState() {

        if (isLogin == false) {
            //未登录
            layoutProFileLogin.visibility = View.GONE
            layoutProfileNotLogin.visibility = View.VISIBLE
        } else {
            //已登陆
            layoutProFileLogin.visibility = View.VISIBLE
            layoutProfileNotLogin.visibility = View.GONE


            //刷新登陆用户信息
            layoutProFileLogin.visibility = View.VISIBLE
            layoutProfileNotLogin.visibility = View.GONE
            if (loginUser != null) {
                viewLoginIcon.setImageResource(R.mipmap.user_avatar)
                textLoginUsername.text = loginUser!!.nickname
                textLoginUserScore.text = "积分：${loginUser!!.coinCount.toString()}"
            }

        }
    }


}