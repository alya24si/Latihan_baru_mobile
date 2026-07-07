package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.databinding.ActivityDetailDistribusiBinding
import com.example.alya_love.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailDistribusiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDistribusiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDistribusiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Detail Distribusi"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val distribusi = db.distribusiDao().getById(id)
                withContext(Dispatchers.Main) {
                    distribusi?.let { bindData(it) } ?: finish()
                }
            }
        } else {
            finish()
        }
    }

    private fun bindData(item: com.example.alya_love.room.DistribusiEntity) {
        binding.tvNamaBarang.text = item.namaBarang
        binding.tvJumlah.text = "Jumlah: ${item.jumlah}"
        binding.tvTujuan.text = "Tujuan: ${item.tujuan}"
        binding.tvTanggal.text = "Tanggal: ${item.tanggal}"
        binding.tvStatus.text = "Status: ${item.status}"
    }
}