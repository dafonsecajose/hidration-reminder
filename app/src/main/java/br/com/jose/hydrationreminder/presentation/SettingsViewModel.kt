package br.com.jose.hydrationreminder.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.domain.settings.GetSettingsUseCase
import br.com.jose.hydrationreminder.domain.settings.UpdateSettingsUseCase
import br.com.jose.hydrationreminder.notifications.NotificationsSchedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SettingsViewModel(
    getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val notificationsSchedule: NotificationsSchedule
): ViewModel() {

    private val _state = MutableLiveData<State>()
    val settings: LiveData<Settings> = getSettingsUseCase.getSettings().asLiveData()
    val state: LiveData<State> = _state

     @RequiresApi(Build.VERSION_CODES.O)
     fun updateSettings(data: ArrayList<String>) {
        viewModelScope.launch {
            updateSettingsUseCase(data)
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect {
                    _state.value = State.Updated
                    notificationsSchedule.createNotifications()
                }
        }
    }

    sealed class State {
        object Loading : State()
        object Updated : State()

        data class Success(val settings: Settings) : State()
        data class Error(val error: Throwable) : State()
    }

    companion object {
        const val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
    }
}