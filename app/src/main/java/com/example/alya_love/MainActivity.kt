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

//[START]
//│
//▼
//[MainActivity.onCreate()]
//│ Penjelasan: Lifecycle pertama Activity dipanggil saat app dibuka
//▼
//[setContentView(activity_main)]
//│ Penjelasan: Inflate layout XML ke memori
//▼
//[setSupportActionBar(toolbar)]
//│ Penjelasan: Mengaktifkan Toolbar sebagai ActionBar untuk menu icon
//▼
//[savedInstanceState == null?] ──[YES]──▶ [loadFragment(HomeFragment())]
//│ Penjelasan: Cek apakah Activity baru dibuat atau di-restore (mencegah fragment double)
//│                                            │
//[NO]                                          │
//│                                            │
//└──────────────┬─────────────────────────────┘
//│
//▼
//[bottomNavigation.setOnItemSelectedListener]
//│ Penjelasan: Listener untuk menangkap event klik item bottom nav
//▼
//[User Klik Menu Item]
//│
//▼
//<item.itemId?>
//│ Penjelasan: Identifikasi menu mana yang diklik berdasarkan ID
//│
//├─[R.id.nav_home]──▶ [updateToolbarTitle("Home")] → [HomeFragment()]
//├─[R.id.nav_about]─▶ [updateToolbarTitle("About")] → [AboutFragment()]
//└─[R.id.nav_profile]→ [updateToolbarTitle("Profile")] → [ProfileFragment()]
//│
//▼
//[loadFragment(fragment)]
//│ Penjelasan: Method helper untuk mengganti fragment
//▼
//[supportFragmentManager.beginTransaction()]
//│ Penjelasan: Mulai transaksi fragment (add/replace/remove)
//▼
//[.replace(R.id.fragment_container, fragment)]
//│ Penjelasan: Ganti fragment lama di container dengan fragment baru
//▼
//[.commit()]
//│ Penjelasan: Eksekusi transaksi secara asynchronous
//▼
//[Fragment Tampil]
//▼
//[END]



//TOOLBAR
//[START]
//│
//▼
//[onCreateOptionsMenu()]
//│ Penjelasan: Dipanggil sistem saat Toolbar dibuat, untuk inflate menu
//▼
//[menuInflater.inflate(R.menu.menu_main)]
//│ Penjelasan: Load file XML menu ke objek Menu
//▼
//[User Klik Menu Icon]
//│
//▼
//[onOptionsItemSelected(item)]
//│ Penjelasan: Callback saat item menu diklik
//▼
//<item.itemId?>
//│ Penjelasan: Identifikasi menu berdasarkan ID
//│
//├─[action_settings]────▶ [Intent(SettingsActivity)] → [startActivity] → [END]
//│   Penjelasan: Buka halaman pengaturan
//├─[action_qr_generator]─▶ [Intent(QRGeneratorActivity)] → [startActivity] → [END]
//│   Penjelasan: Buka halaman generate QR
//├─[action_qr_scanner]───▶ [Intent(QRScannerActivity)] → [startActivity] → [END]
//│   Penjelasan: Buka halaman scan QR
//└─[action_camera_intent]▶ [Intent(CameraIntentActivity)] → [startActivity] → [END]
//Penjelasan: Buka halaman kamera