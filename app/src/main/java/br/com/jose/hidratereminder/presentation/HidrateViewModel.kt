package br.com.jose.hidratereminder.presentation

import androidx.lifecycle.*
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.domain.settings.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HidrateViewModel(
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase()
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect { _state.value = State.Success(it) }
        }
    }

    sealed class State {
        object Loading : State()

        data class Success(val settings: Settings) : State()
        data class Error(val error: Throwable) : State()
    }
}