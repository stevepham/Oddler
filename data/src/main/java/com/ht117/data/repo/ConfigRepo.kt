package com.ht117.data.repo

import com.ht117.data.model.UiState
import com.ht117.data.source.local.ConfigLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface IConfigRepo {
    fun getUserID(): Flow<UiState<String>>
}

class ConfigRepoImpl: IConfigRepo {
    override fun getUserID() = flow {
        emit(UiState.Success(ConfigLocal.getAccountID()))
    }
}

