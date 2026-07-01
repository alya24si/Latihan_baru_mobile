package com.example.alya_love.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionHelper {

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(
        launcher: ActivityResultLauncher<String>,
        permission: String
    ) {
        launcher.launch(permission)
    }

    fun isNotificationPermissionRequired(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }

    fun getNotificationPermission(): String {
        return Manifest.permission.POST_NOTIFICATIONS
    }

    // ✅ TAMBAHAN BARU UNTUK KAMERA & STORAGE
    val CAMERA_PERMISSION: String = Manifest.permission.CAMERA

    val CAMERA_PERMISSIONS: Array<String> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    fun allCameraPermissionsGranted(context: Context): Boolean {
        return CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestCameraPermissions(
        launcher: ActivityResultLauncher<Array<String>>,
        permissions: Array<String> = CAMERA_PERMISSIONS
    ) {
        launcher.launch(permissions)
    }
}