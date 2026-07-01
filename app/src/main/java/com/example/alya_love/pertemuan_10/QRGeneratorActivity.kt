package com.example.alya_love.pertemuan_10

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.alya_love.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class QRGeneratorActivity : AppCompatActivity() {

    private lateinit var etQRText: EditText
    private lateinit var ivQRCode: ImageView
    private lateinit var btnGenerateQR: Button
    private lateinit var btnSaveQR: Button

    private var currentQRBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_generator)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        etQRText = findViewById(R.id.etQRText)
        ivQRCode = findViewById(R.id.ivQRCode)
        btnGenerateQR = findViewById(R.id.btnGenerateQR)
        btnSaveQR = findViewById(R.id.btnSaveQR)
    }

    private fun setupListeners() {
        btnGenerateQR.setOnClickListener {
            val text = etQRText.text.toString().trim()
            if (text.isNotEmpty()) {
                generateQRCode(text)
            } else {
                Toast.makeText(this, "Masukkan teks terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        btnSaveQR.setOnClickListener {
            currentQRBitmap?.let { bitmap ->
                saveQRToGallery(bitmap)
            }
        }
    }

    private fun generateQRCode(text: String) {
        try {
            // ✅ FIX: Gunakan MultiFormatWriter() bukan getInstance()
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                text,
                BarcodeFormat.QR_CODE,
                500,
                500
            )

            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }

            currentQRBitmap = bitmap
            ivQRCode.setImageBitmap(bitmap)
            btnSaveQR.visibility = android.view.View.VISIBLE

            Toast.makeText(this, "QR Code berhasil dibuat", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal membuat QR Code: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveQRToGallery(bitmap: Bitmap) {
        try {
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val customFolder = File(picturesDir, "AlyaLove_QRCodes")

            if (!customFolder.exists()) {
                customFolder.mkdirs()
            }

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFile = File(customFolder, "QR_$timestamp.png")

            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
            }

            val values = android.content.ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/AlyaLove_QRCodes")
            }

            val resolver = contentResolver
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            uri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            }

            Toast.makeText(this, "QR Code disimpan ke Galeri > Pictures > AlyaLove_QRCodes", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal menyimpan: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}