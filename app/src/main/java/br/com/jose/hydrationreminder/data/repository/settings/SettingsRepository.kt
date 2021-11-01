package br.com.jose.hydrationreminder.data.repository.settings

import br.com.jose.hydrationreminder.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository{
    val readProto: Flow<Settings>
    suspend fun updateValue(
        weight: Double,
        beginTime: String,
        endTime: String,
        amountToDrink: Int
    )
}