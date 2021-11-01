package br.com.jose.hydrationreminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_history_drinks")
data class HistoryDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: String,
    val hour: String,
    val amount: Double
)