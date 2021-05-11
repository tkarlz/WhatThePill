package com.pill.what.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM History ORDER BY id DESC")
    fun getAll(): LiveData<List<History>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: History)
    @Update
    fun update(history: History)
    @Delete
    fun delete(history: History)
    @Query("DELETE FROM History")
    fun deleteAll()
}