package br.com.jose.hidratereminder.domain.settings


import br.com.jose.hidratereminder.core.UseCase
import br.com.jose.hidratereminder.data.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateSettingsUseCase(
    private val repositoryImpl: SettingsRepository
): UseCase.NoSource<ArrayList<String>>(){
    override suspend fun execute(param: ArrayList<String>): Flow<Unit> {
        return flow {
            repositoryImpl.updateValue(param[0].toDouble(), param[1], param[2], param[3].toInt())
            emit(Unit)
        }
    }
}