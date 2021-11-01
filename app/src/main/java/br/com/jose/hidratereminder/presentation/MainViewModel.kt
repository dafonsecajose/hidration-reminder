package br.com.jose.hidratereminder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.domain.settings.GetSettingsUseCase

class MainViewModel(
    getSettingsUseCase: GetSettingsUseCase
):ViewModel() {
        val settings: LiveData<Settings> = getSettingsUseCase.getSettings().asLiveData()
}