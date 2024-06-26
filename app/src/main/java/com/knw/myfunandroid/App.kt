package com.knw.myfunandroid

import android.app.Application
import android.content.Context
import com.knw.myfunandroid.logic.model.User
import java.util.ArrayList


class App : Application() {
    companion object {
        private var _isLogin: Boolean = false
        private var _loginUser: User? = null
        private var _cookies: MutableList<String>? = null
        private var  _searchHistoryList : ArrayList<String> = ArrayList<String>()

        var searchHistoryList :ArrayList<String>?
            get() {
                return _searchHistoryList
            }set(value)
        {
                _searchHistoryList = value!!
            }

        var cookies: MutableList<String>?
            get() {
                return _cookies
            }
            set(value) {
                _cookies = value
            }

        var loginUser: User?
            get() {
                return _loginUser
            }
            set(value) {
                _loginUser = value
            }
        var isLogin: Boolean
            get() {
                return _isLogin
            }
            set(value) {
                _isLogin = value
            }

        private var sContext: Context? = null

        fun getsContext(): Context? {
            return sContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sContext = applicationContext
    }
}
