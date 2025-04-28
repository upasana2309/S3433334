package com.example.eventreminderapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventreminderapp.data.model.Holiday
import com.example.eventreminderapp.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HolidayUiState {
    data object Loading : HolidayUiState()
    data class Success(val holidays: List<Holiday>) : HolidayUiState()
    data class Error(val message: String) : HolidayUiState()
}

class HolidayViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<HolidayUiState>(HolidayUiState.Loading)
    val uiState: StateFlow<HolidayUiState> = _uiState

    fun fetchHolidays(countryCode: String = "UK", year: Int = 2025) {
        _uiState.value = HolidayUiState.Loading

        viewModelScope.launch {
            try {
                val holidays = RetrofitInstance.api.getPublicHolidays(year, countryCode)
                _uiState.value = HolidayUiState.Success(holidays)
            } catch (e: Exception) {
                _uiState.value = HolidayUiState.Error("Failed to fetch holidays: ${e.localizedMessage}")
            }
        }
    }
}
