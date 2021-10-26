package br.com.jose.hidratereminder.data.repository.settings

import br.com.jose.hidratereminder.Settings
import kotlinx.coroutines.flow.Flow
import java.time.temporal.TemporalAmount

interface SettingsRepository{
    val readProto: Flow<Settings>
    suspend fun updateValue(
        weight: Double,
        beginTime: String,
        endTime: String,
        amountToDrink: Int
    )
}