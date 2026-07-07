package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DistribusiDao {
    @Query("SELECT * FROM distribusi")
    suspend fun getAll(): List<DistribusiEntity>

    @Query("SELECT * FROM distribusi WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): DistribusiEntity?

    @Insert
    suspend fun insert(distribusi: DistribusiEntity)

    @Update
    suspend fun update(distribusi: DistribusiEntity)

    @Delete
    suspend fun delete(distribusi: DistribusiEntity)
}