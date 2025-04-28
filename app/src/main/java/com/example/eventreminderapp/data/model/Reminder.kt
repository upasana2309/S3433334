package com.example.eventreminderapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val timestamp: Long,
    val isHoliday: Boolean = false
)
