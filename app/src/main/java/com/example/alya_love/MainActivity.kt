package com.example.alya_love

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.alya_love.settings.SettingsActivity
import com.example.alya_love.pertemuan_10.QRGeneratorActivity
import com.example.alya_love.pertemuan_10.QRScannerActivity
import com.example.alya_love.pertemuan_10.CameraIntentActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            updateToolbarTitle("Home")
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> { updateToolbarTitle("Home"); HomeFragment() }
                R.id.nav_about -> { updateToolbarTitle("About"); AboutFragment() }
                R.id.nav_profile -> { updateToolbarTitle("Profile"); ProfileFragment() }
                else -> { updateToolbarTitle("Home"); HomeFragment() }
            }
            loadFragment(fragment)
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun updateToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            // ✅ HANDLE MENU BARU
            R.id.action_qr_generator -> {
                startActivity(Intent(this, QRGeneratorActivity::class.java))
                true
            }
            R.id.action_qr_scanner -> {
                startActivity(Intent(this, QRScannerActivity::class.java))
                true
            }
            R.id.action_camera_intent -> {
                startActivity(Intent(this, CameraIntentActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}