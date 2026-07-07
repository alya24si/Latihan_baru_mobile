package com.example.alya_love.pertemuan_10

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alya_love.databinding.ActivityDonasiFormBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.DonasiEntity
import kotlinx.coroutines.launch

class DonasiFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonasiFormBinding
    private lateinit var db: AppDatabase
    private var donasiId: Int = -1
    private var currentDonasi: DonasiEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonasiFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        donasiId = intent.getIntExtra("EXTRA_ID", -1)

        if (donasiId != -1) {
            loadDonasi()
        }

        binding.btnSimpan.setOnClickListener {
            simpanDonasi()
        }
    }

    private fun loadDonasi() {
        lifecycleScope.launch {
            val donasi = db.donasiDao().getById(donasiId)
            donasi?.let {
                currentDonasi = it
                binding.etNamaDonatur.setText(it.namaDonatur)
                binding.etJenisDonasi.setText(it.jenisDonasi)
                binding.etNilai.setText(it.nilai)
                binding.etKejadian.setText(it.kejadian)
            } ?: finish()
        }
    }

    private fun simpanDonasi() {
        val namaDonatur = binding.etNamaDonatur.text.toString().trim()
        val jenisDonasi = binding.etJenisDonasi.text.toString().trim()
        val nilai = binding.etNilai.text.toString().trim()
        val kejadian = binding.etKejadian.text.toString().trim()

        if (namaDonatur.isEmpty() || jenisDonasi.isEmpty() || nilai.isEmpty() || kejadian.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            if (donasiId != -1) {
                // Update
                val updated = DonasiEntity(
                    id = donasiId,
                    namaDonatur = namaDonatur,
                    jenisDonasi = jenisDonasi,
                    nilai = nilai,
                    kejadian = kejadian
                )
                db.donasiDao().update(updated)
            } else {
                // Insert
                val newDonasi = DonasiEntity(
                    namaDonatur = namaDonatur,
                    jenisDonasi = jenisDonasi,
                    nilai = nilai,
                    kejadian = kejadian
                )
                db.donasiDao().insert(newDonasi)
            }

            Toast.makeText(this@DonasiFormActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}

//CREATE DONASI (Tambah Baru)
//[START]
//│
//▼
//[DonasiBencanaFragment → Klik FAB (+)]
//│
//▼
//[Intent ke DonasiFormActivity]
//│ (TIDAK kirim EXTRA_ID)
//▼
//[ActivityDonasiFormBinding.inflate()]
//│
//▼
//[db = AppDatabase.getDatabase(this)]
//│
//▼
//[donasiId = intent.getIntExtra("EXTRA_ID", -1)]
//│
//▼
//<donasiId != -1?> ──[YA]──▶ [loadDonasi()] → [UPDATE FLOW]
//│
//[TIDAK] (donasiId = -1)
//│
//▼
//[User Input: etNamaDonatur, etJenisDonasi, etNilai, etKejadian]
//│
//▼
//[User Klik btnSimpan]
//│
//▼
//[simpanDonasi()]
//│
//▼
//<Validasi: namaDonatur/jenisDonasi/nilai/kejadian isEmpty()?> ──[YA]──▶ [Toast "Semua field wajib diisi"] ──▶ [END]
//│
//[TIDAK]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//<donasiId != -1?> ──[YA]──▶ [db.donasiDao().update(updated)]
//│
//[TIDAK]
//│
//▼
//[db.donasiDao().insert(newDonasi)]
//│
//▼
//[Toast "Data berhasil disimpan"]
//│
//▼
//[setResult(RESULT_OK)]
//│
//▼
//[finish()]
//│
//▼
//[Fragment onActivityResult() → loadData() → Refresh RecyclerView]
//│
//▼
//[END]


//UPDATE DONASI (Edit Data)
//[START]
//│
//▼
//[RecyclerView Item → Klik btnEdit]
//│
//▼
//[Intent ke DonasiFormActivity]
//│
//▼
//[intent.putExtra("EXTRA_ID", donasi.id)]
//│
//▼
//[ActivityDonasiFormBinding.inflate()]
//│
//▼
//[donasiId = intent.getIntExtra("EXTRA_ID", -1)]
//│
//▼
//<donasiId != -1?> ──[YA]──▶ [loadDonasi()]
//│
//[TIDAK]
//│
//▼
//[loadDonasi()]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//[val donasi = db.donasiDao().getById(donasiId)]
//│
//▼
//<donasi != null?> ──[TIDAK]──▶ [finish()] ──▶ [END]
//│
//[YA]
//│
//▼
//[currentDonasi = donasi]
//│
//▼
//[Form Terisi Otomatis:]
//├─ etNamaDonatur.setText(donasi.namaDonatur)
//├─ etJenisDonasi.setText(donasi.jenisDonasi)
//├─ etNilai.setText(donasi.nilai)
//└─ etKejadian.setText(donasi.kejadian)
//│
//▼
//[User Ubah Data]
//│
//▼
//[User Klik btnSimpan]
//│
//▼
//[simpanDonasi()]
//│
//▼
//<Validasi Input?> ──[GAGAL]──▶ [Toast Error] ──▶ [END]
//│
//[BERHASIL]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//<donasiId != -1?> ──[YA]──▶ [db.donasiDao().update(updated)]
//│
//[TIDAK]
//│
//▼
//[db.donasiDao().insert(newDonasi)]
//│
//▼
//[Toast "Data berhasil disimpan"]
//│
//▼
//[setResult(RESULT_OK)]
//│
//▼
//[finish()]
//│
//▼
//[Fragment onActivityResult() → loadData() → Refresh RecyclerView]
//│
//▼
//[END]