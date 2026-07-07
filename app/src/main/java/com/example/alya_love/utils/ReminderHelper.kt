package com.example.alya_love.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object ReminderHelper {

    fun setReminder(
        context: Context,
        minutesFromNow: Int,
        title: String,
        message: String,
        targetActivity: Class<*>
    ) {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MINUTE, minutesFromNow)
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("message", message)
            putExtra("target_activity", targetActivity.name)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun cancelReminder(context: Context, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            alarmManager.cancel(it)
        }
    }
}

//[START]
//▼
//[User Klik Menu "Reminder"]
//▼
//[onOptionsItemSelected(action_reminder)]
//▼
//[showReminderDialog()]
//│ Penjelasan: Tampilkan dialog custom untuk set reminder
//▼
//[DialogReminderBinding.inflate()]
//▼
//[currentTab = viewPager.currentItem]
//│ Penjelasan: Ambil posisi tab aktif untuk judul reminder
//▼
//<currentTab?> → [tabName: "Kejadian"/"Posko"/"Donasi"/...]
//▼
//[AlertDialog.Builder.setView(dialogBinding.root).show()]
//│ Penjelasan: Bangun dan tampilkan dialog
//▼
//[User Input etMinutes]
//▼
//[User Klik btnSet]
//▼
//[minutes = etMinutes.text.toString().toIntOrNull()]
//│ Penjelasan: Konversi input ke Int, null jika gagal
//▼
//<minutes == null || minutes <= 0?> ──[YES]──▶ [Toast "Masukkan menit valid"] → [END]
//│ Penjelasan: Validasi input harus angka positif
//[NO]
//▼
//[ReminderHelper.setReminder(context, minutes, title, message, TenthActivity)]
//│ Penjelasan: Method helper untuk set alarm
//▼
//[AlarmManager.setExactAndAllowWhileIdle()]
//│ Penjelasan: Set alarm yang tetap jalan meski HP idle/doze mode
//▼
//[Sistem Menunggu Waktu Tercapai]
//│ Penjelasan: OS Android handle di background
//▼
//[ReminderReceiver.onReceive()]
//│ Penjelasan: BroadcastReceiver menerima broadcast dari AlarmManager
//▼
//[NotificationHelper.showNotification()]
//│ Penjelasan: Build notification dengan channel, icon, intent
//▼
//[Notifikasi Muncul]
//▼
//[END]