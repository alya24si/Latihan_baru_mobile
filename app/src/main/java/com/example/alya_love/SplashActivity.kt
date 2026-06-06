package com.example.alya_love

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.onboarding.OnBoardingActivity
import com.example.alya_love.pertemuan_3.ThirdActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loginPref = getSharedPreferences("LOGIN", MODE_PRIVATE)
        val appPref = getSharedPreferences("APP", MODE_PRIVATE)

        val isLogin = loginPref.getBoolean("isLogin", false)
        val isOnboardingDone = appPref.getBoolean("ONBOARDING", false)

        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(
                Intent(
                    this,
                    OnBoardingActivity::class.java
                )
            )

            finish()

        }, 2000)
    }
}