package com.knw.myfunandroid.ui.profile;

import android.os.Bundle;
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.fragment.app.Fragment;
import com.knw.myfunandroid.App.Companion.loginUser
import com.knw.myfunandroid.R
import com.knw.myfunandroid.logic.model.LoginResponse
import com.knw.myfunandroid.logic.network.ServiceCreator
import com.knw.myfunandroid.logic.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class SignUpFragment : Fragment() {
    private var passwordIsHiden = true
    private var passwordIsHidenConfirm = true
    private lateinit var buttonSignUp:TextView
    private lateinit var imgBackLoginPage:ImageView
    private lateinit var imgSignHidePassword:ImageView
    private lateinit var imgSignHidePasswordConfirm:ImageView
    private lateinit var editSignPassword:EditText
    private lateinit var editSignPasswordConfim:EditText
    private lateinit var editSignUsername:EditText


    override fun onCreateView(
            inflater:LayoutInflater,
            container:ViewGroup?,
            savedInstanceState:Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        initView(view)
        initListener()
        return  view
    }

    private fun initListener() {
        buttonSignUp.setOnClickListener({
            //判断用户名、密码、确认密码是否合法、用户数据写入数据库
            if(editSignUsername.text.isEmpty()||editSignPassword.text.isEmpty()||editSignPasswordConfim.text.isEmpty())
            {
             Toast.makeText(context,"是不是忘记输入了什么？",Toast.LENGTH_SHORT).show()
            }else
            {
                if((editSignPassword.text.toString()!=editSignPasswordConfim.text.toString()))
                {
                    Toast.makeText(context,"密码不一致哦，请检查一下",Toast.LENGTH_SHORT).show()
                    editSignPassword.text.clear()
                    editSignPasswordConfim.text.clear()
                }else{
               //真正发送注册请求
                    val  userService = ServiceCreator.create<UserService>()
                    userService.userSignUp(editSignUsername.text.toString(),editSignPassword.text.toString(),editSignPasswordConfim.text.toString()).
                    enqueue(object :
                        Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                            if (response.isSuccessful) {
                                val loginResponse = response.body()
                                Log.d("loginResponse",loginResponse.toString())
                                if (loginResponse?.errorCode == -1) {
                                    // 注册失败
                                    editSignUsername.setText("")
                                    editSignPassword.setText("")
                                    editSignPasswordConfim.setText("")
                                    Toast.makeText(context, "抱歉,"+loginResponse.errorMsg, Toast.LENGTH_SHORT).show()
                                } else {
                                    // 注册成功

                                    loginUser = loginResponse?.data

                                    Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show()
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


                }
            }


        })
        imgBackLoginPage.setOnClickListener({
            requireActivity().supportFragmentManager.popBackStack()
        })
        imgSignHidePassword.setOnClickListener({
            if (passwordIsHiden) {
                passwordIsHiden = false
                imgSignHidePassword.setImageResource(R.mipmap.mimayincang_press)
                editSignPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordIsHiden = true
                imgSignHidePassword.setImageResource(R.mipmap.mimayincang_normal)
                editSignPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // 切换光标和密码类型
            editSignPassword.requestFocus()
            editSignPassword.setSelection(editSignPassword.text.length)

        })
        imgSignHidePasswordConfirm.setOnClickListener({

            if (passwordIsHidenConfirm) {
                passwordIsHidenConfirm = false
                imgSignHidePasswordConfirm.setImageResource(R.mipmap.mimayincang_press)
                editSignPasswordConfim.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                passwordIsHidenConfirm = true
                imgSignHidePasswordConfirm.setImageResource(R.mipmap.mimayincang_normal)
                editSignPasswordConfim.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            // 切换光标和密码类型
            editSignPasswordConfim.requestFocus()
            editSignPasswordConfim.setSelection(editSignPasswordConfim.text.length)

        })
    }


    private fun initView(view: View) {
       buttonSignUp= view.findViewById<TextView>(R.id.button_sign_up);
       imgBackLoginPage= view.findViewById<ImageView>(R.id.back_login_page)
        imgSignHidePassword =view.findViewById<ImageView>(R.id.sign_hide_password)
       imgSignHidePasswordConfirm=  view.findViewById<ImageView>(R.id.sign_hide_password_confirm)
       editSignUsername= view.findViewById<EditText>(R.id.edit_sign_username)
       editSignPassword= view.findViewById<EditText>(R.id.edit_sign_password)
       editSignPasswordConfim= view.findViewById<EditText>(R.id.edit_sign_password_confirm)

    }
}
