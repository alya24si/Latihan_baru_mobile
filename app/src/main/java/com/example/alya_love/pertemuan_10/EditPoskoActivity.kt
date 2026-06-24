package com.example.alya_love.pertemuan_10

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.databinding.ActivityEditPoskoBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.PoskoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditPoskoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPoskoBinding
    private var currentPosko: PoskoEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPoskoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Edit Posko"
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }

        val db = AppDatabase.getDatabase(this)
        val id = intent.getIntExtra("EXTRA_ID", -1)

        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val posko = db.poskoDao().getById(id)
                withContext(Dispatchers.Main) {
                    posko?.let {
                        currentPosko = it
                        binding.etNamaPosko.setText(it.namaPosko)
                        binding.etAlamat.setText(it.alamat)
                        binding.etKapasitas.setText(it.kapasitas)
                        binding.etPenanggungJawab.setText(it.penanggungJawab)
                        binding.etTelepon.setText(it.telepon)
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
        val namaPosko = binding.etNamaPosko.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()
        val kapasitas = binding.etKapasitas.text.toString().trim()
        val penanggungJawab = binding.etPenanggungJawab.text.toString().trim()
        val telepon = binding.etTelepon.text.toString().trim()

        if (namaPosko.isEmpty() || alamat.isEmpty() || kapasitas.isEmpty() ||
            penanggungJawab.isEmpty() || telepon.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val original = currentPosko ?: return

        val updated = PoskoEntity(
            id = original.id,
            namaPosko = namaPosko,
            alamat = alamat,
            kapasitas = kapasitas,
            penanggungJawab = penanggungJawab,
            telepon = telepon,
            gambar = original.gambar
        )

        CoroutineScope(Dispatchers.IO).launch {
            db.poskoDao().update(updated)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@EditPoskoActivity, "Data posko berhasil diperbarui", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}