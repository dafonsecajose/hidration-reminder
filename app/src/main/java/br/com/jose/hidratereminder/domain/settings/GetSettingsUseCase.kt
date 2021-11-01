package br.com.jose.hidratereminder.domain.settings

import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.core.UseCase
import br.com.jose.hidratereminder.data.repository.settings.SettingsRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class GetSettingsUseCase(
    private val repositoryImpl: SettingsRepository
){
   fun getSettings(): Flow<Settings>{
       return repositoryImpl.readProto
   }
}