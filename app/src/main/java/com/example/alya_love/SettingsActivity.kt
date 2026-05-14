package com.example.alya_love.settings

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.R
import com.example.alya_love.databinding.ActivitySettingsBinding
import com.google.android.material.chip.Chip

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var adapter: SettingsAdapter

    // Data awal ListView
    private val allItems = listOf(
        InfoItem("Kebijakan Privasi", "Cara kami mengumpulkan & melindungi data", R.drawable.ic_privacy, "Privacy"),
        InfoItem("Syarat & Ketentuan", "Aturan penggunaan layanan", R.drawable.ic_privacy, "Privacy"),
        InfoItem("Tentang Aplikasi", "Versi 1.0 | Developer: Alya", R.drawable.ic_about, "About"),
        InfoItem("Lisensi Open Source", "Library pihak ketiga yang digunakan", R.drawable.ic_about, "About"),
        InfoItem("Panduan Penggunaan", "Cara mengoperasikan fitur utama", R.drawable.ic_help, "Help"),
        InfoItem("FAQ", "Pertanyaan yang sering diajukan", R.drawable.ic_help, "Help")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Settings & Info"
            setDisplayHomeAsUpEnabled(true)
        }

        // ✅ ChipGroup Filter
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip = group.findViewById<Chip>(checkedIds[0])
                adapter.filterByCategory(chip.text.toString())
            }
        }

        // ✅ GridLayout Klik → Scroll & Filter ListView
        binding.cardPrivacy.setOnClickListener { filterAndScroll("Privacy") }
        binding.cardAbout.setOnClickListener { filterAndScroll("About") }
        binding.cardHelp.setOnClickListener { filterAndScroll("Help") }
        binding.cardContact.setOnClickListener {
            Toast.makeText(this, "Email: support@alyalove.com", Toast.LENGTH_SHORT).show()
        }

        // ✅ ListView Setup
        adapter = SettingsAdapter(this, allItems) { item ->
            Toast.makeText(this, "Dibuka: ${item.title}", Toast.LENGTH_SHORT).show()
        }
        binding.listViewInfo.adapter = adapter

        // ✅ MaterialButton & TextInputLayout Validasi
        binding.btnSend.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val msg = binding.etMessage.text.toString().trim()
            when {
                name.isEmpty() -> { binding.etName.error = "Wajib diisi" }
                msg.isEmpty() -> { binding.etMessage.error = "Wajib diisi" }
                else -> {
                    Toast.makeText(this, "Terima kasih, $name! Pesan terkirim ✅", Toast.LENGTH_LONG).show()
                    binding.etName.text?.clear()
                    binding.etMessage.text?.clear()
                }
            }
        }
    }

    private fun filterAndScroll(cat: String) {
        adapter.filterByCategory(cat)
        binding.listViewInfo.smoothScrollToPosition(0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}