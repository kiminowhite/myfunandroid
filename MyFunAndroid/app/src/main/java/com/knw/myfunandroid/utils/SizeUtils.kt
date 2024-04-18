package com.knw.myfunandroid.utils

import com.knw.myfunandroid.App

object SizeUtils {
    fun dip2px(dpValue: Float): Int {
        val scale = App.getsContext()?.resources?.displayMetrics?.density ?: 0f
        return (dpValue * scale + 0.5f).toInt()
    }
}
