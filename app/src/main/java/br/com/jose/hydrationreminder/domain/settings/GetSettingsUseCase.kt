package br.com.jose.hydrationreminder.domain.settings

import br.com.jose.hydrationreminder.Settings
import br.com.jose.hydrationreminder.data.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(
    private val repositoryImpl: SettingsRepository
){
   fun getSettings(): Flow<Settings>{
       return repositoryImpl.readProto
   }
}