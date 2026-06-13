package com.example.alya_love.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PoskoDao {

    @Query("SELECT * FROM posko")
    suspend fun getAll(): List<PoskoEntity>

    @Insert
    suspend fun insert(posko: PoskoEntity)

    @Delete
    suspend fun delete(posko: PoskoEntity)
}