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

//ALUR SETTINGS (Filter + ListView)
//[START]
//▼
//[SettingsActivity.onCreate()]
//▼
//[ActivitySettingsBinding.inflate()]
//▼
//[allItems = listOf(6 InfoItem: Privacy, About, Help)]
//│ Penjelasan: Data statis untuk ListView
//▼
//[adapter = SettingsAdapter(this, allItems)]
//│ Penjelasan: Custom adapter untuk ListView
//▼
//[listViewInfo.adapter = adapter]
//▼
//[chipGroupFilter.setOnCheckedStateChangeListener]
//│ Penjelasan: Listener untuk ChipGroup (filter kategori)
//▼
//[User Klik Chip (Privacy/About/Help)]
//▼
//[adapter.filterByCategory(chip.text)]
//│ Penjelasan: Filter list berdasarkan kategori chip
//▼
//[ListView Terfilter]
//▼
//[User Klik Card (Privacy/About/Help/Contact)]
//▼
//<card?>
//├─[cardPrivacy]──▶ [filterAndScroll("Privacy")]
//│   Penjelasan: Filter list + scroll ke atas
//├─[cardAbout]────▶ [filterAndScroll("About")]
//├─[cardHelp]─────▶ [filterAndScroll("Help")]
//└─[cardContact]──▶ [Toast "Email: support@alyalove.com"]
//▼
//[User Isi Form (etName, etMessage)]
//▼
//[User Klik btnSend]
//▼
//<name.isEmpty()?> ──[YES]──▶ [etName.error = "Wajib diisi"] → [END]
//│ Penjelasan: Validasi field nama
//[NO]
//▼
//<msg.isEmpty()?> ──[YES]──▶ [etMessage.error = "Wajib diisi"] → [END]
//│ Penjelasan: Validasi field pesan
//[NO]
//▼
//[Toast "Terima kasih, $name! Pesan terkirim ✅"]
//▼
//[etName.text.clear()] → [etMessage.text.clear()]
//│ Penjelasan: Reset form setelah submit
//▼
//[END]