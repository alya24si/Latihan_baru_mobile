package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LogistikDao {
    @Query("SELECT * FROM logistik")
    suspend fun getAll(): List<LogistikEntity>

    @Query("SELECT * FROM logistik WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): LogistikEntity?

    @Insert
    suspend fun insert(logistik: LogistikEntity)

    @Update
    suspend fun update(logistik: LogistikEntity)

    @Delete
    suspend fun delete(logistik: LogistikEntity)
}