package com.example.alya_love.pertemuan_10

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alya_love.databinding.ActivityLogistikFormBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.LogistikEntity
import kotlinx.coroutines.launch

class LogistikFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogistikFormBinding
    private lateinit var db: AppDatabase
    private var logistikId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogistikFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)
        logistikId = intent.getIntExtra("EXTRA_ID", -1)

        if (logistikId != -1) {
            loadLogistik()
        }

        binding.btnSimpan.setOnClickListener {
            simpanLogistik()
        }
    }

    private fun loadLogistik() {
        lifecycleScope.launch {
            val logistik = db.logistikDao().getById(logistikId)
            logistik?.let {
                binding.etNamaBarang.setText(it.namaBarang)
                binding.etSatuan.setText(it.satuan)
                binding.etStok.setText(it.stok.toString())
                binding.etSumber.setText(it.sumber)
                binding.etKejadian.setText(it.kejadian)
            } ?: finish()
        }
    }

    private fun simpanLogistik() {
        val namaBarang = binding.etNamaBarang.text.toString().trim()
        val satuan = binding.etSatuan.text.toString().trim()
        val stokStr = binding.etStok.text.toString().trim()
        val sumber = binding.etSumber.text.toString().trim()
        val kejadian = binding.etKejadian.text.toString().trim()

        if (namaBarang.isEmpty() || satuan.isEmpty() || stokStr.isEmpty() ||
            sumber.isEmpty() || kejadian.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val stok = stokStr.toIntOrNull()
        if (stok == null) {
            binding.etStok.error = "Stok harus berupa angka"
            binding.etStok.requestFocus()
            return
        }

        lifecycleScope.launch {
            if (logistikId != -1) {
                val updated = LogistikEntity(
                    id = logistikId,
                    namaBarang = namaBarang,
                    satuan = satuan,
                    stok = stok,
                    sumber = sumber,
                    kejadian = kejadian
                )
                db.logistikDao().update(updated)
            } else {
                val newLogistik = LogistikEntity(
                    namaBarang = namaBarang,
                    satuan = satuan,
                    stok = stok,
                    sumber = sumber,
                    kejadian = kejadian
                )
                db.logistikDao().insert(newLogistik)
            }

            Toast.makeText(this@LogistikFormActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        }
    }
}

//CREATE LOGISTIK (Tambah Baru)
//[START]
//│
//▼
//[LogistikFragment → Klik FAB (+)]
//│
//▼
//[Intent ke LogistikFormActivity]
//│ (TIDAK kirim EXTRA_ID)
//▼
//[ActivityLogistikFormBinding.inflate()]
//│
//▼
//[db = AppDatabase.getDatabase(this)]
//│
//▼
//[logistikId = intent.getIntExtra("EXTRA_ID", -1)]
//│
//▼
//<logistikId != -1?> ──[YA]──▶ [loadLogistik()] → [UPDATE FLOW]
//│
//[TIDAK] (logistikId = -1)
//│
//▼
//[User Input: etNamaBarang, etSatuan, etStok, etSumber, etKejadian]
//│
//▼
//[User Klik btnSimpan]
//│
//▼
//[simpanLogistik()]
//│
//▼
//<Validasi: namaBarang/satuan/stok/sumber/kejadian isEmpty()?> ──[YA]──▶ [Toast "Semua field wajib diisi"] ──▶ [END]
//│
//[TIDAK]
//│
//▼
//[val stok = stokStr.toIntOrNull()]
//│
//▼
//<stok == null?> ──[YA]──▶ [etStok.error = "Stok harus berupa angka"] → [END]
//│
//[TIDAK]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//<logistikId != -1?> ──[YA]──▶ [db.logistikDao().update(updated)]
//│
//[TIDAK]
//│
//▼
//[db.logistikDao().insert(newLogistik)]
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



//UPDATE LOGISTIK (Edit Data)
//[START]
//│
//▼
//[RecyclerView Item → Klik btnEdit]
//│
//▼
//[Intent ke LogistikFormActivity]
//│
//▼
//[intent.putExtra("EXTRA_ID", logistik.id)]
//│
//▼
//[ActivityLogistikFormBinding.inflate()]
//│
//▼
//[logistikId = intent.getIntExtra("EXTRA_ID", -1)]
//│
//▼
//<logistikId != -1?> ──[YA]──▶ [loadLogistik()]
//│
//[TIDAK]
//│
//▼
//[loadLogistik()]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//[val logistik = db.logistikDao().getById(logistikId)]
//│
//▼
//<logistik != null?> ──[TIDAK]──▶ [finish()] ──▶ [END]
//│
//[YA]
//│
//▼
//[Form Terisi Otomatis:]
//├─ etNamaBarang.setText(logistik.namaBarang)
//├─ etSatuan.setText(logistik.satuan)
//├─ etStok.setText(logistik.stok.toString())
//├─ etSumber.setText(logistik.sumber)
//└─ etKejadian.setText(logistik.kejadian)
//│
//▼
//[User Ubah Data]
//│
//▼
//[User Klik btnSimpan]
//│
//▼
//[simpanLogistik()]
//│
//▼
//<Validasi: Semua field kosong?> ──[YA]──▶ [Toast Error] ──▶ [END]
//│
//[TIDAK]
//│
//▼
//[val stok = stokStr.toIntOrNull()]
//│
//▼
//<stok == null?> ──[YA]──▶ [etStok.error = "Stok harus angka"] ──▶ [END]
//│
//[TIDAK]
//│
//▼
//[lifecycleScope.launch]
//│
//▼
//<logistikId != -1?> ──[YA]──▶ [db.logistikDao().update(updated)]
//│
//[TIDAK]
//│
//▼
//[db.logistikDao().insert(newLogistik)]
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