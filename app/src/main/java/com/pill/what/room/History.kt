package com.pill.what.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class History(
        var name: String,
        var time: String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}