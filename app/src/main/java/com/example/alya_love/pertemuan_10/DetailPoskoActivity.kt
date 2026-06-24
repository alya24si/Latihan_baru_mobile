package com.example.alya_love.pertemuan_10

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.alya_love.databinding.ActivityDetailPoskoBinding
import com.example.alya_love.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailPoskoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPoskoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPoskoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Posko"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val posko = db.poskoDao().getById(id)
                withContext(Dispatchers.Main) {
                    posko?.let { bindData(it) } ?: finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun bindData(item: com.example.alya_love.room.PoskoEntity) {
        binding.tvNamaPosko.text = item.namaPosko
        binding.tvAlamat.text = item.alamat
        binding.tvKapasitas.text = item.kapasitas
        binding.tvPenanggungJawab.text = item.penanggungJawab
        binding.tvTelepon.text = item.telepon

        if (item.gambar.isNotEmpty()) {
            Glide.with(this)
                .load(Uri.parse(item.gambar))
                .into(binding.ivGambar)
        } else {
            binding.ivGambar.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }
}