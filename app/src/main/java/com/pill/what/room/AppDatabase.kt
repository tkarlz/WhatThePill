package com.pill.what.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun historyDao(): HistoryDao

        companion object{
            private var INSTANCE: AppDatabase? = null
            @Synchronized
            fun getInstance(context: Context): AppDatabase {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            "history_database")
                            .allowMainThreadQueries()
                            .build()
                }
                return INSTANCE as AppDatabase
            }
        }
    }
    