package br.com.jose.hydrationreminder.data.di

import br.com.jose.hydrationreminder.data.database.AppDatabase
import br.com.jose.hydrationreminder.data.repository.history.HistoryRepository
import br.com.jose.hydrationreminder.data.repository.history.HistoryRepositoryImpl
import br.com.jose.hydrationreminder.data.repository.settings.SettingsRepository
import br.com.jose.hydrationreminder.data.repository.settings.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModules {

    fun load() {
        loadKoinModules(repositoryModule() + databaseModule())
    }

    private fun repositoryModule(): Module {
        return module {
            single<HistoryRepository> { HistoryRepositoryImpl(get()) }
            single<SettingsRepository> { SettingsRepositoryImpl(androidApplication()) }
        }
    }

    private fun databaseModule(): Module{
        return module {
            single { AppDatabase.getInstance(androidApplication()) }
        }
    }
}