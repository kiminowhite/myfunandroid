package com.knw.myfunandroid.ui.official

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.knw.myfunandroid.R

class OfficialFragment :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     val view =    inflater.inflate(R.layout.fragment_offcial,container,false)
        return view
    }
}