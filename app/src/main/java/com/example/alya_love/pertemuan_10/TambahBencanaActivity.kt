package com.example.alya_love.pertemuan_10

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alya_love.R
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.BencanaEntity
import kotlinx.coroutines.launch

class TambahBencanaActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private var selectedImageUri: Uri? = null

    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri != null) {
                selectedImageUri = uri

                findViewById<ImageView>(R.id.ivPreview)
                    .setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_bencana)

        db = AppDatabase.getDatabase(this)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val etLokasi = findViewById<EditText>(R.id.etLokasi)
        val etTanggal = findViewById<EditText>(R.id.etTanggal)

        val btnPilihGambar = findViewById<Button>(R.id.btnPilihGambar)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnPilihGambar.setOnClickListener {
            imagePicker.launch("image/*")
        }

        btnSimpan.setOnClickListener {

            if (etJudul.text.isEmpty() ||
                etDeskripsi.text.isEmpty() ||
                etLokasi.text.isEmpty() ||
                etTanggal.text.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Lengkapi semua data terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.bencanaDao().insert(
                    BencanaEntity(
                        judul = etJudul.text.toString(),
                        deskripsi = etDeskripsi.text.toString(),
                        lokasi = etLokasi.text.toString(),
                        tanggal = etTanggal.text.toString(),
                        gambar = selectedImageUri?.toString() ?: ""
                    )
                )

                Toast.makeText(
                    this@TambahBencanaActivity,
                    "Data berhasil disimpan",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
        }
    }
}