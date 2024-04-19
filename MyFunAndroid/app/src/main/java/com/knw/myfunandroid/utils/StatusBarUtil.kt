package com.knw.myfunandroid.utils

import android.app.Activity
import android.os.Build
import androidx.core.content.ContextCompat

object StatusBarUtil {
    fun setStatusBarColor(activity: Activity, colorResId: Int) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(activity, colorResId)
        }
    }
}
