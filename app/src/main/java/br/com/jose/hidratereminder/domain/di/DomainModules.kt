package br.com.jose.hidratereminder.domain.di

import br.com.jose.hidratereminder.domain.settings.GetSettingsUseCase
import br.com.jose.hidratereminder.domain.settings.UpdateSettingsUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModules {
    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetSettingsUseCase(get()) }
            factory { UpdateSettingsUseCase(get()) }
        }

    }
}