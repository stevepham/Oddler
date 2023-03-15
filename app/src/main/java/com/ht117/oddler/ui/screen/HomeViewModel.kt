package com.ht117.oddler.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ht117.data.model.UiState
import com.ht117.data.repo.IConfigRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.shareIn

class HomeViewModel(private val configRepo: IConfigRepo): ViewModel() {

    val uiState: SharedFlow<UiState<String>>
        get() = configRepo.getUserID()
            .flowOn(Dispatchers.IO)
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000)
            )
}
