package com.example.alya_love.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        BencanaEntity::class,
        PoskoEntity::class,
        DonasiEntity::class,
        DistribusiEntity::class,
        LogistikEntity::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bencanaDao(): BencanaDao
    abstract fun poskoDao(): PoskoDao
    abstract fun donasiDao(): DonasiDao
    abstract fun distribusiDao(): DistribusiDao
    abstract fun logistikDao(): LogistikDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bencana_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}