package com.sunstrinq.lifecountdown

import android.app.Application
import com.sunstrinq.lifecountdown.data.UserPreferencesRepository

class MainApp : Application() {
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(this)
    }
}
