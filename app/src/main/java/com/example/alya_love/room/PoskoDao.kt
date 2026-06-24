package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PoskoDao {

    @Query("SELECT * FROM posko")
    suspend fun getAll(): List<PoskoEntity>

    @Query("SELECT * FROM posko WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): PoskoEntity?  // ← TAMBAHKAN INI

    @Insert
    suspend fun insert(posko: PoskoEntity)

    @Update
    suspend fun update(posko: PoskoEntity)   // ← TAMBAHKAN INI

    @Delete
    suspend fun delete(posko: PoskoEntity)
}