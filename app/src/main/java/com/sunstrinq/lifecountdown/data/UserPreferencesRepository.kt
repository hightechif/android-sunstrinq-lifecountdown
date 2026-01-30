package com.sunstrinq.lifecountdown.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        private val USER_BIRTH_DATE_EPOCH_DAY = longPreferencesKey("user_birth_date")
        private val LIFE_EXPECTANCY_YEARS = intPreferencesKey("life_expectancy_years")
    }

    data class UserPreferences(
        val birthDate: LocalDate? = null,
        val lifeExpectancy: Int = 80
    )

    val userPreferences: Flow<UserPreferences> = dataStore.data
        .map { preferences ->
            val epochDay = preferences[USER_BIRTH_DATE_EPOCH_DAY]
            val birthDate = if (epochDay != null) LocalDate.ofEpochDay(epochDay) else null
            val lifeExpectancy = preferences[LIFE_EXPECTANCY_YEARS] ?: 80

            UserPreferences(birthDate, lifeExpectancy)
        }

    val birthDate: Flow<LocalDate?> = userPreferences.map { it.birthDate }

    suspend fun saveBirthDate(date: LocalDate) {
        dataStore.edit { preferences ->
            preferences[USER_BIRTH_DATE_EPOCH_DAY] = date.toEpochDay()
        }
    }

    suspend fun saveLifeExpectancy(years: Int) {
        dataStore.edit { preferences ->
            preferences[LIFE_EXPECTANCY_YEARS] = years
        }
    }
}
