package com.arafat1419.argames.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arafat1419.argames.core.data.local.entities.GameEntity

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun appDao(): AppDao
}