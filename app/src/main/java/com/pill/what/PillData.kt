package com.pill.what

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PillData(
    @PrimaryKey(autoGenerate = false)
    var name: String,
    var forms: String,
    var print_front: String,
    var print_back: String,
    var shapes: String,
    var color1: String,
    var color2: String,
    var line_front: String,
    var line_back: String
)
