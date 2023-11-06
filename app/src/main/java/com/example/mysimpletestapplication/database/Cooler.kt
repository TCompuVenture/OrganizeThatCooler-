package com.example.mysimpletestapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cooler_table")
data class Cooler(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val foodName: String,
    val foodType: String
) {

}