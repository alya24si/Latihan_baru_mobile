package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BencanaDao {

    @Query("SELECT * FROM bencana")
    suspend fun getAll(): List<BencanaEntity>

    @Insert
    suspend fun insert(bencana: BencanaEntity)

    @Delete
    suspend fun delete(bencana: BencanaEntity)
}