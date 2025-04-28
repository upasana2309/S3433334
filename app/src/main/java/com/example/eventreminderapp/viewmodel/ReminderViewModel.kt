package com.example.eventreminderapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.eventreminderapp.data.database.ReminderDatabase
import com.example.eventreminderapp.data.model.Reminder
import com.example.eventreminderapp.data.repository.ReminderRepository
import com.example.eventreminderapp.notifications.ReminderWorker
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.concurrent.TimeUnit

enum class ReminderFilter {
    ALL, UPCOMING, PAST
}

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    // List of public holidays to restrict reminder creation
    @SuppressLint("NewApi")
    val holidayDates: List<LocalDate> = listOf(
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2025, 1, 26),
        LocalDate.of(2025, 4, 14),
        LocalDate.of(2025, 8, 15),
        LocalDate.of(2025, 10, 2),
        LocalDate.of(2025, 12, 25)
        // Add more holidays as needed
    )

    private val repository: ReminderRepository
    private val _searchQuery = MutableStateFlow("")
    private val _filter = MutableStateFlow(ReminderFilter.ALL)

    private val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    val allReminders: Flow<List<Reminder>>

    val filteredReminders: Flow<List<Reminder>>

    init {
        val dao = ReminderDatabase.getDatabase(application).reminderDao()
        repository = ReminderRepository(dao)
        allReminders = repository.allReminders

        filteredReminders = combine(repository.allReminders, _searchQuery, _filter) { reminders, query, filter ->
            reminders
                .filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
                .filter {
                    when (filter) {
                        ReminderFilter.ALL -> true
                        ReminderFilter.UPCOMING -> it.timestamp >= currentTimeMillis
                        ReminderFilter.PAST -> it.timestamp < currentTimeMillis
                    }
                }
        }
    }

    fun searchReminders(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(filter: ReminderFilter) {
        _filter.value = filter
    }

    fun addReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.insertReminder(reminder)

            val workManager = WorkManager.getInstance(getApplication())

            val data = Data.Builder()
                .putString("title", reminder.title)
                .putString("description", reminder.description)
                .build()

            val delayMillis = reminder.timestamp - System.currentTimeMillis()
            if (delayMillis > 0) {
                val reminderRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
                    .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .build()

                workManager.enqueue(reminderRequest)
            }
        }
    }

    fun updateReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.updateReminder(reminder)
        }
    }

    fun deleteReminder(reminder: Reminder) {
        viewModelScope.launch {
            repository.deleteReminder(reminder)
        }
    }
}