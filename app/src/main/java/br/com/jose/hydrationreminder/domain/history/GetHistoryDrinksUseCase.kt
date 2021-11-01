package br.com.jose.hydrationreminder.domain.history

import br.com.jose.hydrationreminder.core.UseCase
import br.com.jose.hydrationreminder.data.model.HistoryDrink
import br.com.jose.hydrationreminder.data.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryDrinksUseCase(
    private val repository: HistoryRepository
): UseCase<String, List<HistoryDrink>>() {
    override suspend fun execute(param: String): Flow<List<HistoryDrink>> {
        return repository.findDrinksByDay(param)
    }
}