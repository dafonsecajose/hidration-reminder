package br.com.jose.hidratereminder.domain.history

import br.com.jose.hidratereminder.core.UseCase
import br.com.jose.hidratereminder.data.model.HistoryDrink
import br.com.jose.hidratereminder.data.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow

class GetHistoryDrinksUseCase(
    private val repository: HistoryRepository
): UseCase<String, List<HistoryDrink>>() {
    override suspend fun execute(param: String): Flow<List<HistoryDrink>> {
        return repository.findDrinksByDay(param)
    }
}