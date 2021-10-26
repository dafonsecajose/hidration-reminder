package br.com.jose.hidratereminder.domain.history

import br.com.jose.hidratereminder.core.UseCase
import br.com.jose.hidratereminder.data.model.HistoryDrink
import br.com.jose.hidratereminder.data.repository.history.HistoryRepository
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