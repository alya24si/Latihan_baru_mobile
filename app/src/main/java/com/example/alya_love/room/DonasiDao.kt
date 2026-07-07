package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DonasiDao {
    @Query("SELECT * FROM donasi")
    suspend fun getAll(): List<DonasiEntity>

    @Query("SELECT * FROM donasi WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): DonasiEntity?

    @Insert
    suspend fun insert(donasi: DonasiEntity)

    @Update
    suspend fun update(donasi: DonasiEntity)

    @Delete
    suspend fun delete(donasi: DonasiEntity)
}