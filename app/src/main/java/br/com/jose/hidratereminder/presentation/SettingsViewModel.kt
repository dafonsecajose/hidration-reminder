package br.com.jose.hidratereminder.presentation

import androidx.lifecycle.*
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.domain.settings.GetSettingsUseCase
import br.com.jose.hidratereminder.domain.settings.UpdateSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SettingsViewModel(
    getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
): ViewModel() {

    private val _state = MutableLiveData<State>()
    val settings: LiveData<Settings> = getSettingsUseCase.getSettings().asLiveData()
    val state: LiveData<State> = _state

     fun updateSettings(data: ArrayList<String>) {
        viewModelScope.launch {
            updateSettingsUseCase(data)
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect { _state.value = State.Updated }
        }
    }

    sealed class State {
        object Loading : State()
        object Updated : State()

        data class Success(val settings: Settings) : State()
        data class Error(val error: Throwable) : State()
    }
}