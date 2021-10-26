package br.com.jose.hidratereminder.data.repository.history

import br.com.jose.hidratereminder.data.model.HistoryDrink
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun findDrinksByDay(date: Long): Flow<List<HistoryDrink>>
    suspend fun save(historyDrink: HistoryDrink)
    suspend fun update(historyDrink: HistoryDrink)
}