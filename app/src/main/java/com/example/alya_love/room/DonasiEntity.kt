package com.example.alya_love.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donasi")
data class DonasiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val namaDonatur: String,
    val jenisDonasi: String,
    val nilai: String,
    val kejadian: String
)