package com.example.alya_love.pertemuan_10

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alya_love.databinding.ActivityDistribusiFormBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.DistribusiEntity
import kotlinx.coroutines.launch

class DistribusiFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDistribusiFormBinding
    private lateinit var db: AppDatabase
    private var distribusiId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDistribusiFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        distribusiId = intent.getIntExtra("EXTRA_ID", -1)

        if (distribusiId != -1) {
            loadDistribusi()
        }

        binding.btnSimpan.setOnClickListener {
            simpanDistribusi()
        }
    }

    private fun loadDistribusi() {
        lifecycleScope.launch {
            val distribusi = db.distribusiDao().getById(distribusiId)
            distribusi?.let {
                binding.etNamaBarang.setText(it.namaBarang)
                binding.etJumlah.setText(it.jumlah)
                binding.etTujuan.setText(it.tujuan)
                binding.etTanggal.setText(it.tanggal)
                binding.etStatus.setText(it.status)
            } ?: finish()
        }
    }

    private fun simpanDistribusi() {
        val namaBarang = binding.etNamaBarang.text.toString().trim()
        val jumlah = binding.etJumlah.text.toString().trim()
        val tujuan = binding.etTujuan.text.toString().trim()
        val tanggal = binding.etTanggal.text.toString().trim()
        val status = binding.etStatus.text.toString().trim()

        if (namaBarang.isEmpty() || jumlah.isEmpty() || tujuan.isEmpty() ||
            tanggal.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            if (distribusiId != -1) {
                val updated = DistribusiEntity(
                    id = distribusiId,
                    namaBarang = namaBarang,
                    jumlah = jumlah,
                    tujuan = tujuan,
                    tanggal = tanggal,
                    status = status
                )
                db.distribusiDao().update(updated)
            } else {
                val newDistribusi = DistribusiEntity(
                    namaBarang = namaBarang,
                    jumlah = jumlah,
                    tujuan = tujuan,
                    tanggal = tanggal,
                    status = status
                )
                db.distribusiDao().insert(newDistribusi)
            }

            Toast.makeText(this@DistribusiFormActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}