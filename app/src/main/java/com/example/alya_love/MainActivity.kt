package com.example.alya_love

import android.content.Intent          // ← TAMBAH INI
import android.view.Menu               // ← TAMBAH INI
import android.view.MenuItem           // ← TAMBAH INI
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.alya_love.settings.SettingsActivity  // ← TAMBAH INI

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

    // ✅ METHOD BARU 1: Buat menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // ✅ METHOD BARU 2: Handle klik menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}