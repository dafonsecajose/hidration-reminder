package br.com.jose.hidratereminder.data.di

import br.com.jose.hidratereminder.data.repository.SettingsRepository
import br.com.jose.hidratereminder.data.repository.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModules {

    fun load() {
        loadKoinModules(dataSettingsModule())
    }

    private fun dataSettingsModule(): Module {
        return module {
            single<SettingsRepository> { SettingsRepositoryImpl(androidApplication().applicationContext) }
        }
    }
}