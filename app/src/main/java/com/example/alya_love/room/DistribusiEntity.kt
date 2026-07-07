package com.example.alya_love.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "distribusi")
data class DistribusiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaBarang: String,
    val jumlah: String,
    val tujuan: String,
    val tanggal: String,
    val status: String
)