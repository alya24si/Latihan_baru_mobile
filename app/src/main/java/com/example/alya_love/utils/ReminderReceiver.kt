package com.example.alya_love.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Pengingat Bina Desa"
        val message = intent.getStringExtra("message") ?: "Waktunya melakukan aktivitas"
        val targetClassName = intent.getStringExtra("target_activity")

        val targetIntent = if (!targetClassName.isNullOrEmpty()) {
            val clazz = try {
                Class.forName(targetClassName)
            } catch (e: ClassNotFoundException) {
                Class.forName("com.example.alya_love.MainActivity")
            }
            Intent(context, clazz).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        } else {
            Intent(context, Class.forName("com.example.alya_love.MainActivity")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }

        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = targetIntent,
            requestCode = System.currentTimeMillis().toInt()
        )
    }
}