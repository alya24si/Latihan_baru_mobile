package com.example.alya_love

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class bangun_ruang : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bangun_ruang)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "🚨 Kalkulator Logistik Darurat"
        }
        toolbar.setNavigationOnClickListener { finish() }

        // Init Views
        val etKorban = findViewById<EditText>(R.id.panjang)
        val etHari = findViewById<EditText>(R.id.lebar)
        val etPosko = findViewById<EditText>(R.id.tinggi)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val tvHasil = findViewById<TextView>(R.id.hasil)

        btnHitung.setOnClickListener {
            val korbanStr = etKorban.text.toString().trim()
            val hariStr = etHari.text.toString().trim()
            val poskoStr = etPosko.text.toString().trim()

            if (korbanStr.isEmpty() || hariStr.isEmpty() || poskoStr.isEmpty()) {
                Toast.makeText(this, "Semua data harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val korban = korbanStr.toInt()
            val hari = hariStr.toInt()
            val posko = poskoStr.toInt()

            if (korban <= 0 || hari <= 0 || posko <= 0) {
                Toast.makeText(this, "Nilai harus lebih dari 0!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 🧮 PERHITUNGAN KEBUTUHAN LOGISTIK
            // Standar bantuan darurat per orang per hari:
            // - Beras: 0.5 kg/orang/hari
            // - Air minum: 3 liter/orang/hari
            // - Makanan siap saji: 2 paket/orang/hari
            // - Selimut: 1 pcs/orang (sekali)
            // - Obat-obatan: 1 paket per 10 orang (sekali)

            val totalBeras = korban * hari * 0.5
            val totalAir = korban * hari * 3.0
            val totalMakanan = korban * hari * 2
            val totalSelimut = korban
            val totalObat = Math.ceil(korban / 10.0).toInt()
            val totalKebutuhanPerPosko = (korban.toDouble() / posko).toInt()

            // Format hasil
            val hasilText = """
                👥 Total Korban: $korban jiwa
                📅 Durasi: $hari hari
                🏕️ Jumlah Posko: $posko posko
                👥 Rata-rata per posko: $totalKebutuhanPerPosko orang
                
                📦 KEBUTUHAN LOGISTIK:
                ━━━━━━━━━━━━━━━━━━━
                🍚 Beras: ${String.format("%.1f", totalBeras)} kg
                💧 Air Minum: ${String.format("%.1f", totalAir)} liter
                🍱 Makanan Siap Saji: $totalMakanan paket
                🛏️ Selimut: $totalSelimut pcs
                💊 Obat-obatan: $totalObat paket
                
                💰 ESTIMASI BIAYA:
                ━━━━━━━━━━━━━━━━━━━
                @ Rp 25.000/orang/hari
                Total: Rp ${String.format("%,d", korban * hari * 25000).replace(",", ".")}
            """.trimIndent()

            tvHasil.text = hasilText

            Toast.makeText(this, "✅ Perhitungan berhasil!", Toast.LENGTH_SHORT).show()
        }
    }
}


//Mulai
//│
//▼
//onCreate()
//│
//▼
//setContentView()
//│
//▼
//setSupportActionBar()
//│
//▼
//findViewById()
//(Menginisialisasi EditText, Button, dan TextView)
//│
//▼
//btnHitung.setOnClickListener()
//│
//▼
//Mengambil input
//(etKorban, etHari, etPosko)
//│
//▼
//Validasi isEmpty()
//│
//├── Ya
//│      │
//│      ▼
//│ Toast.makeText()
//│      │
//│      ▼
//│   Selesai
//│
//└── Tidak
//│
//▼
//toInt()
//(Konversi String ke Integer)
//│
//▼
//Validasi nilai > 0
//│
//├── Tidak
//│      │
//│      ▼
//│ Toast.makeText()
//│      │
//│      ▼
//│   Selesai
//│
//└── Ya
//│
//▼
//Hitung:
//- totalBeras
//- totalAir
//- totalMakanan
//- totalSelimut
//- totalObat
//- totalKebutuhanPerPosko
//│
//▼
//Menyusun hasil
//(hasilText)
//│
//▼
//tvHasil.text = hasilText
//│
//▼
//Toast.makeText()
//("Perhitungan berhasil")
//│
//▼
//Selesai