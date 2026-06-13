package com.example.alya_love.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posko")
data class PoskoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val namaPosko: String,
    val alamat: String,
    val kapasitas: String,
    val penanggungJawab: String,
    val telepon: String,
    val gambar: String
)

