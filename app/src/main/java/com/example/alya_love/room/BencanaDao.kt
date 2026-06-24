package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update  // ← Tambahkan import ini

@Dao
interface BencanaDao {

    @Query("SELECT * FROM bencana")
    suspend fun getAll(): List<BencanaEntity>

    @Query("SELECT * FROM bencana WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): BencanaEntity?  // ← TAMBAHKAN INI

    @Insert
    suspend fun insert(bencana: BencanaEntity)

    @Update
    suspend fun update(bencana: BencanaEntity)   // ← TAMBAHKAN INI

    @Delete
    suspend fun delete(bencana: BencanaEntity)
}