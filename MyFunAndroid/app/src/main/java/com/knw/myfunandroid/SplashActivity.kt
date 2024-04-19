package com.knw.myfunandroid

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.knw.myfunandroid.utils.StatusBarUtil

class SplashActivity : AppCompatActivity() {
    private lateinit var splashSkip: TextView
    private lateinit var handler: Handler
    private var countdown: Int = 5
    private lateinit var runnable:Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        splashSkip = findViewById(R.id.splash_skip)
        splashSkip.setOnClickListener {
            // 在点击跳过按钮时取消延迟任务
            handler.removeCallbacks(runnable)

            // 跳转到主界面
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 创建 Handler 对象
        handler = Handler(Looper.getMainLooper())

        // 开始倒计时
        startCountdown()
    }

    /*
    定时任务实现跳转
     */

    private fun startCountdown() {
        // 定义倒计时任务
        runnable = object : Runnable {
            override fun run() {
                if (countdown > 0) {
                    splashSkip.text = "跳过 ($countdown)"
                    countdown--
                    // 1 秒后再次执行任务
                    handler.postDelayed(this, 1000)
                } else {
                    // 倒计时结束，跳转到主界面
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        handler.post(runnable)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}