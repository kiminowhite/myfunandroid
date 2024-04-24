package com.knw.myfunandroid.ui.profile

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsAnimation
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.knw.myfunandroid.App.Companion.isLogin
import com.knw.myfunandroid.App.Companion.loginUser
import com.knw.myfunandroid.R

import com.knw.myfunandroid.logic.model.LoginResponse
import com.knw.myfunandroid.logic.model.User
import com.knw.myfunandroid.logic.network.ServiceCreator
import com.knw.myfunandroid.logic.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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


            val  userService = ServiceCreator.create<UserService>()

           // 发起登录请求
            userService.userLogin(editUsername.text.toString(),editPassword.text.toString()).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Log.d("loginResponse",loginResponse.toString())
                        if (loginResponse?.errorCode == -1) {
                            // 登陆失败
                            isLogin = false
                            editUsername.setText("")
                            editPassword.setText("")
                            Toast.makeText(context, "抱歉,"+loginResponse.errorMsg, Toast.LENGTH_SHORT).show()
                        } else {
                            // 登录成功
                            isLogin = true
                            loginUser = loginResponse?.data

                            val profileFragment = requireActivity().supportFragmentManager.findFragmentByTag("ProfileFragment") as ProfileFragment
                            profileFragment.refreshLoginState()
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                    } else {
                        Log.d("login", "网络请求失败！")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("login", "网络请求失败！")
                }
            })






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