package br.com.jose.hidratereminder.data.repository

import android.content.Context
import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.data.settings.SettingsSerializer.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

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