package com.knw.myfunandroid

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment

class ProfileFragment :Fragment() {
    private  var isLogin:Boolean =false ;
    private lateinit var profileLoginLayout : RelativeLayout
    private lateinit var profileNotLoginLayout : RelativeLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View =  inflater.inflate(R.layout.fragment_profile, container, false)
       profileLoginLayout = view.findViewById<RelativeLayout>(R.id.profile_login_layout)
       profileNotLoginLayout= view.findViewById<RelativeLayout>(R.id.profile_not_login_layout)
        if(isLogin==false)
        {
            profileLoginLayout.visibility =View.GONE
            profileNotLoginLayout.visibility=View.VISIBLE
        }else{
            profileLoginLayout.visibility=View.VISIBLE
            profileNotLoginLayout.visibility=View.GONE
        }

        return view
    }


}