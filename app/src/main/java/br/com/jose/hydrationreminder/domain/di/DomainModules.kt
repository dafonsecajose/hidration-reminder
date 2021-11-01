package br.com.jose.hydrationreminder.domain.di

import br.com.jose.hydrationreminder.domain.history.GetHistoryDrinksUseCase
import br.com.jose.hydrationreminder.domain.history.GetTotalDrinksUseCase
import br.com.jose.hydrationreminder.domain.history.SaveHistoryDrinkUserCase
import br.com.jose.hydrationreminder.domain.history.UpdateHistoryUseCase
import br.com.jose.hydrationreminder.domain.settings.GetSettingsUseCase
import br.com.jose.hydrationreminder.domain.settings.UpdateSettingsUseCase
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
            factory { GetHistoryDrinksUseCase(get()) }
            factory { SaveHistoryDrinkUserCase(get()) }
            factory { UpdateHistoryUseCase(get()) }
            factory { GetTotalDrinksUseCase(get()) }
        }
    }
}