package br.com.jose.hydrationreminder.data.database.dao

import androidx.room.*
import br.com.jose.hydrationreminder.data.model.HistoryDrink
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDrinkDao {

    @Query("SELECT * FROM tb_history_drinks WHERE date = :date ORDER BY hour DESC")
    fun findDrinksByDay(date: String): Flow<List<HistoryDrink>>

    @Query("SELECT SUM(amount) FROM tb_history_drinks WHERE date = :date ")
    fun findTotalDrinksByDay(date: String): Flow<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: HistoryDrink)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: HistoryDrink)
}