package com.example.alya_love.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bencana")
data class BencanaEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val judul: String,
    val deskripsi: String,
    val lokasi: String,
    val tanggal: String,
    val gambar: String
)