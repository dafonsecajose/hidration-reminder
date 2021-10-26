package br.com.jose.hidratereminder.presentation.di

import br.com.jose.hidratereminder.presentation.HidrateViewModel
import br.com.jose.hidratereminder.presentation.SettingsViewModel
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
            viewModel { SettingsViewModel(get(), get()) }
            viewModel { HidrateViewModel(get()) }
        }
    }


}