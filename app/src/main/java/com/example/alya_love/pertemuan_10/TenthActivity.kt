package com.example.alya_love.pertemuan_10

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.R
import com.example.alya_love.databinding.ActivityTenthBinding
import com.example.alya_love.databinding.DialogReminderBinding
import com.example.alya_love.utils.NotificationHelper
import com.example.alya_love.utils.PermissionHelper
import com.example.alya_love.utils.ReminderHelper
import com.google.android.material.tabs.TabLayoutMediator

class TenthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTenthBinding

    // ========== TAMBAHAN: Permission Launcher ==========
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifikasi diizinkan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifikasi ditolak", Toast.LENGTH_SHORT).show()
            }
        }
    // ===================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTenthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "GUARDIANNET"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // ========== TAMBAHAN: Request Permission ==========
        requestNotificationPermission()
        // =================================================

        setupViewPager()
    }

    // ========== TAMBAHAN: Method Baru ==========
    private fun requestNotificationPermission() {
        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (!PermissionHelper.hasPermission(this, permission)) {
                PermissionHelper.requestPermission(notificationPermissionLauncher, permission)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tenth, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reminder -> {
                showReminderDialog()
                true
            }
            R.id.action_test_notification -> {
                testNotification()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showReminderDialog() {
        val dialogBinding = DialogReminderBinding.inflate(layoutInflater)
        val currentTab = binding.viewPager.currentItem
        val tabName = when (currentTab) {
            0 -> "Kejadian Bencana"
            1 -> "Posko Pengungsian"
            2 -> "Donasi"
            3 -> "Logistik"
            4 -> "Distribusi"
            else -> "Aplikasi"
        }

        dialogBinding.tvTabInfo.text = "Reminder untuk tab: $tabName"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()

        dialogBinding.btnSet.setOnClickListener {
            val minutes = dialogBinding.etMinutes.text.toString().toIntOrNull()

            if (minutes == null || minutes <= 0) {
                Toast.makeText(this, "Masukkan jumlah menit yang valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ReminderHelper.setReminder(
                context = this,
                minutesFromNow = minutes,
                title = "⏰ Reminder $tabName",
                message = "Waktunya mengecek update $tabName di GUARDIANNET",
                targetActivity = TenthActivity::class.java
            )

            Toast.makeText(
                this,
                "Reminder diatur: $minutes menit lagi untuk $tabName",
                Toast.LENGTH_LONG
            ).show()

            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun testNotification() {
        val intent = Intent(this, TenthActivity::class.java)
        NotificationHelper.showNotification(
            context = this,
            title = "🚨 GUARDIANNET",
            message = "Ini adalah test notifikasi langsung",
            intent = intent
        )
        Toast.makeText(this, "Notifikasi dikirim!", Toast.LENGTH_SHORT).show()
    }
    // ============================================

    private fun setupViewPager() {
        val tabsAdapter = TenthTabsAdapter(this)
        binding.viewPager.adapter = tabsAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Kejadian"
                1 -> tab.text = "Posko"
                2 -> tab.text = "Donasi"
                3 -> tab.text = "Logistik"
                4 -> tab.text = "Distribusi"
            }
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}


//ALUR TAB LAYOUT
//[START]
//│
//▼
//[TenthActivity.onCreate()]
//▼
//[ActivityTenthBinding.inflate()]
//▼
//[setSupportActionBar(toolbar)]
//│ Penjelasan: Aktifkan toolbar dengan title "GUARDIANNET"
//▼
//[requestNotificationPermission()]
//│ Penjelasan: Minta izin POST_NOTIFICATIONS (wajib di Android 13+)
//▼
//[PermissionHelper.isNotificationPermissionRequired?] ──[YES]──▶ [requestPermission()]
//│ Penjelasan: Cek versi Android & status izin
//▼
//[setupViewPager()]
//│ Penjelasan: Setup ViewPager2 + TabLayout
//▼
//[TenthTabsAdapter(this)]
//│ Penjelasan: Adapter yang menyediakan 5 fragment untuk 5 tab
//▼
//[viewPager.adapter = tabsAdapter]
//▼
//[TabLayoutMediator(tabLayout, viewPager).attach()]
//│ Penjelasan: Sinkronkan TabLayout dengan ViewPager2
//▼
//[User Swipe/Klik Tab]
//▼
//<position?>
//│ Penjelasan: Adapter dipanggil untuk buat fragment sesuai posisi
//│
//├─[0] → [KejadianBencanaFragment()]
//├─[1] → [PoskoBencanaFragment()]
//├─[2] → [DonasiBencanaFragment()]
//├─[3] → [LogistikFragment()]
//└─[4] → [DistribusiFragment()]
//▼
//[Fragment Tampil]
//▼
//[END]
