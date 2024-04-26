package com.knw.myfunandroid.logic.network.interceptor

import android.util.Log
import com.knw.myfunandroid.App
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        // 从响应头中获取 Set-Cookie 字段
        val cookies = originalResponse.headers("Set-Cookie")

        // 检查 App.cookies 是否为 null，如果为 null，则创建一个新的 ArrayList
        if (App.cookies == null) {
            App.cookies = ArrayList()
        }
        // 将接收到的cookie存储到App中
        App.cookies?.addAll(cookies)

        // 打印接收到的cookie
        cookies.forEach { cookie ->
            Log.d("Received Cookie", cookie)
        }

        // 打印存储在App中的cookie
        Log.d("Stored Cookies", App.cookies.toString())

        return originalResponse
    }
}

