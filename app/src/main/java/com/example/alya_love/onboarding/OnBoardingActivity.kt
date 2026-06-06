package com.example.alya_love.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.alya_love.R
import com.example.alya_love.databinding.ActivityOnBoardingBinding
import com.example.alya_love.pertemuan_3.ThirdActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val list = listOf(

            OnBoardItem(
                R.drawable.disaster,
                "Pantau Bencana\nSecara Real-Time",
                "Dapatkan informasi bencana terkini dengan cepat, akurat, dan langsung dari sumber terpercaya."
            ),

            OnBoardItem(
                R.drawable.donation,
                "Bersama Kita\nBisa Membantu",
                "Temukan posko bantuan, kebutuhan darurat, dan salurkan donasi untuk mereka yang membutuhkan."
            ),

            OnBoardItem(
                R.drawable.rocket,
                "Siap Menjadi\nBagian dari Aksi?",
                "Mulai sekarang dan bantu sebarkan informasi penting demi keselamatan dan kemanusiaan."
            )
        )

        binding.viewPager.adapter =
            OnBoardAdapter(list)

        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if (position == list.size - 1) {
                        binding.btnMulai.visibility = View.VISIBLE
                    } else {
                        binding.btnMulai.visibility = View.GONE
                    }
                }
            }
        )

        binding.btnMulai.setOnClickListener {

            getSharedPreferences(
                "APP",
                MODE_PRIVATE
            )
                .edit()
                .putBoolean("ONBOARDING", true)
                .apply()

            startActivity(
                Intent(
                    this,
                    ThirdActivity::class.java
                )
            )

            finish()
        }
    }
}