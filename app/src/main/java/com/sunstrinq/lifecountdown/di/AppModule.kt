package com.sunstrinq.lifecountdown.di

import com.sunstrinq.lifecountdown.data.UserPreferencesRepository
import com.sunstrinq.lifecountdown.ui.viewmodel.LifeCountdownViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { UserPreferencesRepository(get()) }
    viewModel { LifeCountdownViewModel(get()) }
}
