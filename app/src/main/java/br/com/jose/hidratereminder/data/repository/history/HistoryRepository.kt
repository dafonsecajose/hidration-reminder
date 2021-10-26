package br.com.jose.hidratereminder.data.repository.history

import br.com.jose.hidratereminder.data.model.HistoryDrink
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun findDrinksByDay(date: String): Flow<List<HistoryDrink>>
    fun findTotalDrinksByDay(date: String): Flow<Double>
    suspend fun save(historyDrink: HistoryDrink)
    suspend fun update(historyDrink: HistoryDrink)
}