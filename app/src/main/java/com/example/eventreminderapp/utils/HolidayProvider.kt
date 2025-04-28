package com.example.eventreminderapp.utils

import com.example.eventreminderapp.data.model.Holiday
import com.example.eventreminderapp.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object HolidayProvider {
    private val holidays = mutableListOf<Holiday>()

    suspend fun loadHolidays(year: Int = 2025, countryCode: String = "US") {
        withContext(Dispatchers.IO) {
            try {
                val holidayList = RetrofitInstance.api.getPublicHolidays(year, countryCode)
                holidays.clear()
                holidays.addAll(holidayList)
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error if needed
            }
        }
    }

    fun isHoliday(date: String): Boolean {
        return holidays.any { it.date == date }
    }

    fun getHolidayName(date: String): String? {
        return holidays.find { it.date == date }?.localName
    }
}
