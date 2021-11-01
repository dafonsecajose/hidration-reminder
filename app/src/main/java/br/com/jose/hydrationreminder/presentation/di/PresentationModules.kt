package br.com.jose.hydrationreminder.presentation.di

import br.com.jose.hydrationreminder.presentation.HidrateViewModel
import br.com.jose.hydrationreminder.presentation.HistoryViewModel
import br.com.jose.hydrationreminder.presentation.MainViewModel
import br.com.jose.hydrationreminder.presentation.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModules {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            viewModel { MainViewModel(get()) }
            viewModel { HistoryViewModel(get()) }
            viewModel { SettingsViewModel(get(), get()) }
            viewModel { HidrateViewModel(get(), get(), get()) }
        }
    }


}