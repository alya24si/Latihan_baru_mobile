package com.example.alya_love.pertemuan_10

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.example.alya_love.R
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class QRScannerActivity : AppCompatActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var tvScanResult: TextView
    private lateinit var btnBackScanner: Button

    private var imageAnalysisUseCase: ImageAnalysis? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null

    private lateinit var barcodeScanner: BarcodeScanner
    private lateinit var cameraExecutor: ExecutorService

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startCamera()
        } else {
            Toast.makeText(this, "Izin kamera diperlukan untuk scan QR Code", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        initViews()
        setupBarcodeScanner()
        cameraExecutor = Executors.newSingleThreadExecutor()

        checkCameraPermission()

        btnBackScanner.setOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        previewView = findViewById(R.id.previewView)
        tvScanResult = findViewById(R.id.tvScanResult)
        btnBackScanner = findViewById(R.id.btnBackScanner)
    }

    private fun setupBarcodeScanner() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()

        barcodeScanner = BarcodeScanning.getClient(options)
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                startCamera()
            }
            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                bindCameraUseCases()
            } catch (e: Exception) {
                Log.e("QRScanner", "Camera initialization failed", e)
                Toast.makeText(this, "Gagal menginisialisasi kamera", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider ?: return

        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        imageAnalysisUseCase = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor) { imageProxy ->
                    processImageProxy(imageProxy)
                }
            }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()

            camera = cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysisUseCase
            )

        } catch (e: Exception) {
            Log.e("QRScanner", "Use case binding failed", e)
            Toast.makeText(this, "Gagal mengaktifkan kamera", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processImageProxy(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        barcode.rawValue?.let { value ->
                            runOnUiThread {
                                tvScanResult.text = "Hasil Scan:\n$value"
                                Toast.makeText(this, "QR Code terdeteksi!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("QRScanner", "Barcode scanning failed", e)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        barcodeScanner.close()
    }
}

//Mulai
//│
//▼
//onCreate()
//│
//▼
//setContentView(R.layout.activity_qr_scanner)
//│
//▼
//initViews()
//(findViewById PreviewView,
//TextView, Button)
//│
//▼
//setupBarcodeScanner()
//(BarcodeScannerOptions +
//BarcodeScanning.getClient())
//│
//▼
//cameraExecutor =
//Executors.newSingleThreadExecutor()
//│
//▼
//checkCameraPermission()
//│
//▼
//ContextCompat.checkSelfPermission()
//│
//┌──┴──────────────────────┐
//│                         │
//Permission Ditolak     Permission Diberikan
//│                         │
//▼                         ▼
//cameraPermission      startCamera()
//Launcher.launch()         │
//│                         ▼
//┌───────────────┐   ProcessCameraProvider
//│ Izin Ditolak  │        │
//│ Toast +       │        ▼
//│ finish()      │   bindCameraUseCases()
//└───────────────┘        │
//▼
//Preview.Builder()
//│
//▼
//ImageAnalysis.Builder()
//│
//▼
//processImageProxy(imageProxy)
//│
//▼
//InputImage.fromMediaImage()
//│
//▼
//barcodeScanner.process(image)
//│
//┌──────────┴──────────┐
//│                     │
//Scan Gagal            Scan Berhasil
//│                     │
//▼                     ▼
//Log Error        barcode.rawValue
//│
//▼
//tvScanResult.text = value
//│
//▼
//Toast "QR Code terdeteksi!"
//│
//▼
//imageProxy.close()
//│
//▼
//onDestroy()
//│
//▼
//cameraExecutor.shutdown()
//barcodeScanner.close()
//│
//▼
//Selesai