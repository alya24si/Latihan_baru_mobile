package com.example.alya_love.pertemuan_10

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.alya_love.databinding.ActivityDetailBencanaBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.BencanaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class DetailBencanaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBencanaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Kejadian"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val bencana = db.bencanaDao().getById(id)
                withContext(Dispatchers.Main) {
                    bencana?.let { bindData(it) } ?: finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun bindData(item: BencanaEntity) {
        binding.tvJudul.text = item.judul
        binding.tvDeskripsi.text = item.deskripsi
        binding.tvLokasi.text = "📍 ${item.lokasi}"
        binding.tvTanggal.text = "📅 ${item.tanggal}"

        if (item.gambar.isNotEmpty()) {
            // ✅ CEK: URL internet atau file lokal?
            if (item.gambar.startsWith("http://") || item.gambar.startsWith("https://")) {
                // URL internet (dari dummy data)
                Glide.with(this)
                    .load(item.gambar)
                    .into(binding.ivGambar)
            } else {
                // File lokal (dari user upload)
                val file = File(item.gambar)
                if (file.exists()) {
                    Glide.with(this)
                        .load(file)
                        .into(binding.ivGambar)
                } else {
                    binding.ivGambar.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            }
        } else {
            binding.ivGambar.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }
}