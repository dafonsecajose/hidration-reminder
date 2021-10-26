package br.com.jose.hidratereminder.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_history_drinks")
data class HistoryDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: Long,
    val amount: Double
)