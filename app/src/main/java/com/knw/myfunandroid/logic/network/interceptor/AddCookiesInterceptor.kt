package com.knw.myfunandroid.logic.network.interceptor

import android.util.Log
import com.knw.myfunandroid.App
import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 从应用程序类中获取保存的 cookie
        val cookies = App.cookies
        cookies?.forEach { cookie ->
            Log.d("Have Cookie", cookie)
        }


        // 创建一个新的请求构建器，并将 cookie 添加到请求头中
        val requestBuilder = chain.request().newBuilder()


        if (cookies != null) {

            // 在添加cookie之前添加日志输出
            Log.d("Add Cookie", "Before adding cookie")
            for (cookie in cookies) {
                requestBuilder.addHeader("Cookie", cookie)
                cookies.forEach { cookie ->
                    Log.d("Add Cookie", cookie)
                }
            }
            // 在添加cookie之后添加日志输出
            Log.d("Add Cookie", "After adding cookie")

        }


        // 继续请求链
        return chain.proceed(requestBuilder.build())
    }
}
