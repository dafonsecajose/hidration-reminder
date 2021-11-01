package br.com.jose.hidratereminder.domain.history

import br.com.jose.hidratereminder.core.UseCase
import br.com.jose.hidratereminder.data.repository.history.HistoryRepository
import kotlinx.coroutines.flow.Flow

class GetTotalDrinksUseCase(
    private val repository: HistoryRepository
): UseCase<String, Double?>() {
    override suspend fun execute(param: String): Flow<Double?> {
        return repository.findTotalDrinksByDay(param)
    }
}