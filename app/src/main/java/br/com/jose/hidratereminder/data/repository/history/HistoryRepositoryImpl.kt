package br.com.jose.hidratereminder.data.repository.history

import br.com.jose.hidratereminder.data.database.AppDatabase
import br.com.jose.hidratereminder.data.model.HistoryDrink
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(
    appDatabase: AppDatabase
): HistoryRepository {

    private val dao = appDatabase.historyDrinkDao()

    override fun findDrinksByDay(date: Long): Flow<List<HistoryDrink>> {
        return dao.findDrinksByDay(date)
    }

    override suspend fun save(historyDrink: HistoryDrink) {
        dao.save(historyDrink)
    }

    override suspend fun update(historyDrink: HistoryDrink) {
        dao.save(historyDrink)
    }
}