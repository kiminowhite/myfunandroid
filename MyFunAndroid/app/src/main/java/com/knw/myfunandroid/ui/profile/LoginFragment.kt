package com.knw.myfunandroid.ui.profile

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.knw.myfunandroid.App.Companion.isLogin
import com.knw.myfunandroid.App.Companion.loginUser
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.User

class LoginFragment : Fragment() {
    private var passwordIsHiden = true
    private lateinit var imgPasswordHiden:ImageView
    private lateinit var editUsername :EditText
    private lateinit var editPassword :EditText
    private lateinit var buttonLogin:TextView
    private lateinit var imgLoginWithWechat:ImageView
    private lateinit var imgLoginWithWeibo:ImageView
    private lateinit var buttonSignUp:TextView
    private lateinit var imgCloseLoginPage:ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_login, container, false)
        initView(view)
        initListener()
        return  view
    }

    private fun initListener() {
        imgPasswordHiden.setOnClickListener {
            if (passwordIsHiden) {
                passwordIsHiden = false
                imgPasswordHiden.setImageResource(R.mipmap.mimayincang_press)
                editPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordIsHiden = true
                imgPasswordHiden.setImageResource(R.mipmap.mimayincang_normal)
                editPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // 切换光标和密码类型
            editPassword.requestFocus()
            editPassword.setSelection(editPassword.text.length)
        }
        buttonLogin.setOnClickListener({
            //获得登陆结果
            //失败
            //isLogin=false
            //成功
            isLogin=true
            loginUser= User("aiges","123456", R.mipmap.aiges,"アイギス",114514,emptyList())

            if(isLogin==false)
            {
                //失败了清空内容
                editUsername.setText("")

                Toast.makeText(context,"账号或密码错误",Toast.LENGTH_SHORT).show()

            }else{
                //成功，修改islogin登陆为成功，返回profileFragment,并且关闭当前fragment，用户信息保存到app或专门仓库


                val profileFragment =  requireActivity().supportFragmentManager.findFragmentByTag("ProfileFragment") as ProfileFragment
                profileFragment.refreshLoginState()
                requireActivity().supportFragmentManager.popBackStack()
            }



        })
        imgCloseLoginPage.setOnClickListener({
            requireActivity().supportFragmentManager.popBackStack()
        })
        buttonSignUp.setOnClickListener({
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in_right, 0,
                    R.anim.slide_in_left,
                    R.anim.slide_out_left
                )
                .add(android.R.id.content, SignUpFragment(), "SignUpFragment")
                .addToBackStack(null)
                .commit()

        })
        imgLoginWithWechat.setOnClickListener({
            Toast.makeText(context,"还未实现微信登陆功能",Toast.LENGTH_SHORT).show()
        })
        imgLoginWithWeibo.setOnClickListener({
            Toast.makeText(context,"还未实现微博登陆功能",Toast.LENGTH_SHORT).show()
        })
    }

    private fun initView(view: View) {
        imgPasswordHiden = view.findViewById<ImageView>(R.id.hide_password)
        editUsername=view.findViewById<EditText>(R.id.edit_username)
        editPassword = view.findViewById<EditText>(R.id.edit_password)
        buttonLogin= view.findViewById<TextView>(R.id.button_login)
        imgLoginWithWechat = view.findViewById<ImageView>(R.id.login_wechat)
        imgLoginWithWeibo = view.findViewById<ImageView>(R.id.login_weibo)
        buttonSignUp = view.findViewById<TextView>(R.id.sign_up)
       imgCloseLoginPage =  view.findViewById<ImageView>(R.id.login_page_close)
    }

}