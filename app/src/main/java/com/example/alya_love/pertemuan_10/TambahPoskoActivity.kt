package com.example.alya_love.pertemuan_10

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.alya_love.R
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.PoskoEntity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class TambahPoskoActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private var imageUri: Uri? = null

    private lateinit var ivPreview: ImageView

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri != null) {
                imageUri = uri
                ivPreview.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_posko)

        db = AppDatabase.getDatabase(this)

        ivPreview = findViewById(R.id.ivPreview)

        val etNamaPosko =
            findViewById<TextInputEditText>(R.id.etNamaPosko)

        val etAlamat =
            findViewById<TextInputEditText>(R.id.etAlamat)

        val etKapasitas =
            findViewById<TextInputEditText>(R.id.etKapasitas)

        val etPenanggungJawab =
            findViewById<TextInputEditText>(R.id.etPenanggungJawab)

        val etTelepon =
            findViewById<TextInputEditText>(R.id.etTelepon)

        findViewById<MaterialButton>(R.id.btnPilihGambar)
            .setOnClickListener {

                galleryLauncher.launch("image/*")
            }

        findViewById<MaterialButton>(R.id.btnSimpan)
            .setOnClickListener {

                if (etNamaPosko.text.toString().isEmpty()) {

                    Toast.makeText(
                        this,
                        "Nama Posko wajib diisi",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                lifecycleScope.launch {

                    db.poskoDao().insert(
                        PoskoEntity(
                            namaPosko = etNamaPosko.text.toString(),
                            alamat = etAlamat.text.toString(),
                            kapasitas = etKapasitas.text.toString(),
                            penanggungJawab = etPenanggungJawab.text.toString(),
                            telepon = etTelepon.text.toString(),
                            gambar = imageUri?.toString() ?: ""
                        )
                    )

                    Toast.makeText(
                        this@TambahPoskoActivity,
                        "Posko berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
    }
}