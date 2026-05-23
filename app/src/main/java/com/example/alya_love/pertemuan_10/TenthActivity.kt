// ✅ Edit: pertemuan_10/TenthActivity.kt

package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.databinding.ActivityTenthBinding
import com.google.android.material.tabs.TabLayoutMediator  // ← Import ini!

class TenthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTenthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTenthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Pertemuan 10"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 🔥 PANGGIL FUNGSI SETUP VIEWPAGER
        setupViewPager()
    }

    // 🔥 FUNGSI BARU: Hubungkan TabLayout + ViewPager2 + Adapter
    private fun setupViewPager() {
        val tabsAdapter = TenthTabsAdapter(this)

        binding.viewPager.adapter = tabsAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Tab A"
                1 -> tab.text = "Tab B"
            }
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}