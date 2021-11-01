package br.com.jose.hydrationreminder.presentation

import androidx.lifecycle.*
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.core.getDateString
import br.com.jose.hydrationreminder.core.getTimeString
import br.com.jose.hydrationreminder.data.model.HistoryDrink
import br.com.jose.hydrationreminder.domain.history.GetTotalDrinksUseCase
import br.com.jose.hydrationreminder.domain.history.SaveHistoryDrinkUserCase
import br.com.jose.hydrationreminder.domain.settings.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*

class HidrateViewModel(
    getSettingsUseCase: GetSettingsUseCase,
    private val saveHistoryDrinkUserCase: SaveHistoryDrinkUserCase,
    private val getTotalDrinksUseCase: GetTotalDrinksUseCase
) : ViewModel(), LifecycleObserver{

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    val settings: LiveData<Settings> = getSettingsUseCase.getSettings().asLiveData()
    private val _totalDrink = MutableLiveData<Double>()
    val totalDrink: LiveData<Double> = _totalDrink
    private val _date = Calendar.getInstance().time


    fun save(){
        viewModelScope.launch {
            saveHistoryDrinkUserCase(createHistory())
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect {
                    getTotalDrink()
                    _state.value = State.Saved
                }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getTotalDrink() {
        viewModelScope.launch {
            getTotalDrinksUseCase(getDateString(_date))
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect {
                    _totalDrink.value = it ?: 0.0
                    _state.value = State.Saved
                }
        }
    }

    private fun createHistory(): HistoryDrink {
        return HistoryDrink(
            0,
            getDateString(_date),
            getTimeString(_date),
            settings.value?.amountToDrink?.toDouble()!!,
        )
    }


    sealed class State {
        object Loading : State()
        object Saved: State()

        data class Error(val error: Throwable) : State()
    }
}