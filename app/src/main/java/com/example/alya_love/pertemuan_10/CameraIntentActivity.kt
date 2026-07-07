package com.example.alya_love.pertemuan_10

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.alya_love.R
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CameraIntentActivity : AppCompatActivity() {

    private lateinit var btnTakePhoto: Button
    private lateinit var ivCapturedPhoto: ImageView
    private lateinit var btnSaveToGallery: Button
    private lateinit var tvImagePath: TextView

    private var currentPhotoPath: String? = null
    private var capturedImageUri: Uri? = null

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launchCameraIntent()
        } else {
            Toast.makeText(this, "Izin kamera diperlukan", Toast.LENGTH_LONG).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            capturedImageUri?.let { uri ->
                displayCapturedImage(uri)
                btnSaveToGallery.visibility = View.VISIBLE
            }
        } else {
            Toast.makeText(this, "Gagal mengambil foto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_intent)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        btnTakePhoto = findViewById(R.id.btnTakePhoto)
        ivCapturedPhoto = findViewById(R.id.ivCapturedPhoto)
        btnSaveToGallery = findViewById(R.id.btnSaveToGallery)
        tvImagePath = findViewById(R.id.tvImagePath)
    }

    private fun setupListeners() {
        btnTakePhoto.setOnClickListener {
            checkCameraPermission()
        }

        btnSaveToGallery.setOnClickListener {
            capturedImageUri?.let { uri ->
                saveToCustomGallery(uri)
            }
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchCameraIntent()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun launchCameraIntent() {
        try {
            val photoFile = createImageFile()

            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                photoFile
            )

            capturedImageUri = photoURI
            takePictureLauncher.launch(photoURI)

        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(this, "Gagal membuat file foto: ${ex.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun displayCapturedImage(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            ivCapturedPhoto.setImageBitmap(bitmap)
            tvImagePath.text = "Foto tersimpan di: $currentPhotoPath"
            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal menampilkan foto", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToCustomGallery(uri: Uri) {
        try {
            val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val customFolder = File(picturesDir, "AlyaLove_Photos")

            if (!customFolder.exists()) {
                customFolder.mkdirs()
            }

            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)

            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFile = File(customFolder, "Photo_$timestamp.jpg")

            FileOutputStream(imageFile).use { out ->
                bitmap?.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
            }

            val values = android.content.ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, imageFile.name)
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/AlyaLove_Photos")
                put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            }

            val resolver = contentResolver
            val galleryUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            galleryUri?.let {
                resolver.openOutputStream(it)?.use { outputStream ->
                    FileInputStream(imageFile).use { input ->
                        input.copyTo(outputStream)
                    }
                }
            }

            Toast.makeText(
                this,
                "Foto disimpan ke Galeri > Pictures > AlyaLove_Photos",
                Toast.LENGTH_LONG
            ).show()

            inputStream?.close()

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Gagal menyimpan: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}

//[START]
//│
//▼
//[Klik btnTakePhoto]
//│
//▼
//[Cek Permission Kamera]
//│
//▼
//<GRANTED?> ──[NO]──▶ [Minta Izin] ─▶ [END]
//│
//[YES]
//│
//▼
//[Buat File Kosong → FileProvider.getUriForFile()]
//│
//▼
//[takePictureLauncher.launch(uri)]
//│
//▼
//[Kamera Terbuka → User Memotret]
//│
//▼
//[Callback success → Tampilkan Preview di ImageView]
//│
//▼
//[User Klik btnSaveToGallery]
//│
//▼
//[MediaStore.insert() → Simpan ke Pictures/AlyaLove_Photos]
//│
//▼
//[Toast "Tersimpan"]
//│
//▼
//[END]