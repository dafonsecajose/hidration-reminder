package br.com.jose.hidratereminder.presentation

import androidx.lifecycle.*
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.core.getDateString
import br.com.jose.hidratereminder.data.model.HistoryDrink
import br.com.jose.hidratereminder.domain.history.GetHistoryDrinksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val getHistoryDrinksUseCase: GetHistoryDrinksUseCase
) : ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getHistory() {
        viewModelScope.launch {
            getHistoryDrinksUseCase(getDateString())
                .flowOn(Dispatchers.Main)
                .onStart { _state.value = State.Loading }
                .catch { _state.value = State.Error(it) }
                .collect { _state.value = State.Success(it) }
        }
    }


    sealed class State {
        object Loading : State()

        data class Success(val list: List<HistoryDrink>) : State()
        data class Error(val error: Throwable) : State()
    }
}