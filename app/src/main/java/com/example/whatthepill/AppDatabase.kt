package com.example.whatthepill

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.whatthepill.TodoDao

@Database(entities = [Todo::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun todoDao(): TodoDao
    }
    