package com.pill.what

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PillInfo(
    @PrimaryKey(autoGenerate = false)
    var name: String,
    var code: String,
    var image: String,
    var ingredient: String,
    var company: String,
    var seq: String
)
