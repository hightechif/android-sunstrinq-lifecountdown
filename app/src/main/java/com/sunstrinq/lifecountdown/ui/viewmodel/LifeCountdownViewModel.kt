package com.sunstrinq.lifecountdown.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunstrinq.lifecountdown.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class LifeCountdownViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferencesRepository.UserPreferences> =
        repository.userPreferences
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserPreferencesRepository.UserPreferences()
            )

    fun updateBirthDate(date: LocalDate) {
        viewModelScope.launch {
            repository.saveBirthDate(date)
        }
    }

    fun updateLifeExpectancy(years: Int) {
        viewModelScope.launch {
            repository.saveLifeExpectancy(years)
        }
    }


}
