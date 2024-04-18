package com.knw.myfunandroid

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment :Fragment() {
    private  var isLogin:Boolean =false ;
    private lateinit var profileLoginLayout : RelativeLayout //登陆成功布局
    private lateinit var profileNotLoginLayout : RelativeLayout //未登录布局
    private lateinit var login:TextView //点击去登陆
    private lateinit var loginUser: User //登陆用户
    private lateinit var loginIcon:CircleImageView //登陆头像
    private lateinit var loginUsername :TextView //登陆用户名称
    private lateinit var loginUserScore:TextView //登陆用户积分
    private lateinit var logout:TextView //退出登陆

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_profile, container, false)
        initView(view)

        login.setOnClickListener(View.OnClickListener {
            //跳转登陆fragment
            requireActivity().supportFragmentManager.
         beginTransaction().
           setCustomAnimations(R.anim.slide_in_up, 0, 0, R.anim.slide_out_down).
                add(android.R.id.content,LoginFragment()).addToBackStack(null).commit()

            //获取登陆结果

          //  isLogin=true
            //刷新显示
            if(isLogin==false)
            {
                //登陆失败在登陆界面就会被卡住，所以其实无所谓，之后修改
                profileLoginLayout.visibility =View.GONE
                profileNotLoginLayout.visibility=View.VISIBLE
            }else{
                //登陆成功，从登陆界面获取到登陆用户信息，图像，积分，收藏列表等
                loginUser= User("yukari","123456",R.mipmap.yukari,"岳羽ゆかり",114514,emptyList())
                profileLoginLayout.visibility=View.VISIBLE
                profileNotLoginLayout.visibility=View.GONE
                loginIcon.setImageResource(loginUser.iconId)
                loginUsername.text=loginUser.fullName
                loginUserScore.text ="积分：${loginUser.score.toString()}"


            }
        })
        logout.setOnClickListener(View.OnClickListener {
            isLogin =false
            refreshLoginState()
        })

        return view
    }

    private fun initView(view: View) {
        profileLoginLayout = view.findViewById<RelativeLayout>(R.id.profile_login_layout)
        profileNotLoginLayout = view.findViewById<RelativeLayout>(R.id.profile_not_login_layout)
        profileLoginLayout.findViewById<CircleImageView>(R.id.login_icon)
        login = view.findViewById<TextView>(R.id.login)
        loginIcon = view.findViewById<CircleImageView>(R.id.login_icon)
        loginUsername=view.findViewById<TextView>(R.id.login_username)
        loginUserScore= view.findViewById<TextView>(R.id.user_socore)
        logout = view.findViewById<TextView>(R.id.logout)


        //进入页面后判断是否登陆，选择显示内容，后期需要做登陆判断修改isLogin
        //这里要写登陆判断的逻辑,默认未登录
        refreshLoginState()

    }

     fun refreshLoginState()
     {

         if (isLogin == false) {
             profileLoginLayout.visibility = View.GONE
             profileNotLoginLayout.visibility = View.VISIBLE
         } else {
             profileLoginLayout.visibility = View.VISIBLE
             profileNotLoginLayout.visibility = View.GONE
         }
     }
}