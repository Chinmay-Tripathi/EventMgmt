package com.example.eventmgmt.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val dateTime: Date,
    val location: String,
    val latitude: Double?,
    val longitude: Double?,
    val imagePath: String? = null
) 