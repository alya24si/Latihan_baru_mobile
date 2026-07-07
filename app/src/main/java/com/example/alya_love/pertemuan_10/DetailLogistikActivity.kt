package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.databinding.ActivityDetailLogistikBinding
import com.example.alya_love.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailLogistikActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLogistikBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLogistikBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Logistik"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val logistik = db.logistikDao().getById(id)
                withContext(Dispatchers.Main) {
                    logistik?.let { bindData(it) } ?: finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun bindData(item: com.example.alya_love.room.LogistikEntity) {
        binding.tvNamaBarang.text = item.namaBarang
        binding.tvSatuan.text = "Satuan: ${item.satuan}"
        binding.tvStok.text = "Stok: ${item.stok} ${item.satuan}"
        binding.tvSumber.text = "Sumber: ${item.sumber}"
        binding.tvKejadian.text = "Kejadian: ${item.kejadian}"
    }
}