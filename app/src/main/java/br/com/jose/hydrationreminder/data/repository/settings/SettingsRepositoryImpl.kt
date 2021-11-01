package br.com.jose.hydrationreminder.data.repository.settings

import android.content.Context
import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.data.settings.SettingsSerializer.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    context: Context
): SettingsRepository {

    private val dataStore = context.settingsDataStore
    override val readProto: Flow<Settings>
        = dataStore.data.map { it }


    override suspend fun updateValue(
        weight: Double,
        beginTime: String,
        endTime: String,
        amountToDrink: Int
    ) {
        dataStore.updateData { currentSettings ->
            currentSettings.toBuilder()
                .setWeight(weight)
                .setBeginTime(beginTime)
                .setEndTime(endTime)
                .setAmountToDrink(amountToDrink)
                .build()
        }
    }


}