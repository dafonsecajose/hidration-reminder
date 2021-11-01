package br.com.jose.hydrationreminder.domain.history

import br.com.jose.hydrationreminder.core.UseCase
import br.com.jose.hydrationreminder.data.model.HistoryDrink
import br.com.jose.hydrationreminder.data.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveHistoryDrinkUserCase(
    private val repository: HistoryRepository
): UseCase.NoSource<HistoryDrink>() {
    override suspend fun execute(param: HistoryDrink): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }
}