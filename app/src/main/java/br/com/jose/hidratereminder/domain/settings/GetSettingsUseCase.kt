package br.com.jose.hidratereminder.domain.settings

import br.com.jose.hidratereminder.Settings
import br.com.jose.hidratereminder.core.UseCase
import br.com.jose.hidratereminder.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(
    private val repositoryImpl: SettingsRepository
): UseCase.NoParam<Settings>() {
    override suspend fun execute(): Flow<Settings> {
        return repositoryImpl.readProto
    }
}