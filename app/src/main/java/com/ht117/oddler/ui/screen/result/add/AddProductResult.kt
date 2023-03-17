package com.ht117.oddler.ui.screen.result.add

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import com.ht117.data.model.Request
import com.ht117.data.model.UiState
import com.ht117.oddler.ui.screen.result.ResultViewModel
import com.ht117.oddler.ui.screen.result.ShowFailed
import com.ht117.oddler.ui.screen.result.ShowLoading
import com.ht117.oddler.ui.screen.result.ShowSuccess
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddProductResult(
    controller: NavHostController,
    req: Request.AddProductRequest,
    viewModel: ResultViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = req, block = {
        viewModel.addProduct(req)
    })

    when (val state = viewModel.addUiState.collectAsState().value) {
        is UiState.Loading -> {
            ShowLoading(controller, req)
        }

        is UiState.Failed -> {
            ShowFailed(controller, state.err, req, viewModel)
        }

        is UiState.Success -> {
            ShowSuccess(controller, req)
        }
    }
}
