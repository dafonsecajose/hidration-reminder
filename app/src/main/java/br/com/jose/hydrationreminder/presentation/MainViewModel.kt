package br.com.jose.hydrationreminder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.domain.settings.GetSettingsUseCase

class MainViewModel(
    getSettingsUseCase: GetSettingsUseCase
):ViewModel() {
        val settings: LiveData<Settings> = getSettingsUseCase.getSettings().asLiveData()
}