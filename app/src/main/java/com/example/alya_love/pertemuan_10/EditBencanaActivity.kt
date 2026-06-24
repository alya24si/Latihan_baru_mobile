package com.example.alya_love.pertemuan_10

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.databinding.ActivityEditBencanaBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.BencanaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditBencanaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBencanaBinding
    private var currentBencana: BencanaEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Edit Kejadian"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val bencana = db.bencanaDao().getById(id)
                withContext(Dispatchers.Main) {
                    bencana?.let {
                        currentBencana = it
                        binding.etJudul.setText(it.judul)
                        binding.etDeskripsi.setText(it.deskripsi)
                        binding.etLokasi.setText(it.lokasi)
                        binding.etTanggal.setText(it.tanggal)
                    } ?: finish()
                }
            }
        } else {
            finish()
        }

        binding.btnSimpan.setOnClickListener {
            simpanPerubahan(db)
        }
    }

    private fun simpanPerubahan(db: AppDatabase) {
        val judul = binding.etJudul.text.toString().trim()
        val deskripsi = binding.etDeskripsi.text.toString().trim()
        val lokasi = binding.etLokasi.text.toString().trim()
        val tanggal = binding.etTanggal.text.toString().trim()

        if (judul.isEmpty() || deskripsi.isEmpty() || lokasi.isEmpty() || tanggal.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val original = currentBencana ?: return

        val updated = BencanaEntity(
            id = original.id,
            judul = judul,
            deskripsi = deskripsi,
            lokasi = lokasi,
            tanggal = tanggal,
            gambar = original.gambar // Gambar tetap sama
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.bencanaDao().update(updated)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@EditBencanaActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}