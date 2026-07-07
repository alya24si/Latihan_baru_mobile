package com.example.alya_love.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logistik")
data class LogistikEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaBarang: String,
    val satuan: String,
    val stok: Int,
    val sumber: String,
    val kejadian: String
)