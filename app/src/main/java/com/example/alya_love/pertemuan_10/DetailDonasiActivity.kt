package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.databinding.ActivityDetailDonasiBinding
import com.example.alya_love.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailDonasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDonasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Donasi"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val donasi = db.donasiDao().getById(id)
                withContext(Dispatchers.Main) {
                    donasi?.let { bindData(it) } ?: finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun bindData(item: com.example.alya_love.room.DonasiEntity) {
        binding.tvNamaDonatur.text = item.namaDonatur
        binding.tvJenisDonasi.text = "Jenis Donasi: ${item.jenisDonasi}"
        binding.tvNilai.text = "Nilai: ${item.nilai}"
        binding.tvKejadian.text = "Kejadian: ${item.kejadian}"
    }
}