package com.ht117.oddler.ui.screen.result.update

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
internal fun UpdateProductResult(
    controller: NavHostController,
    req: Request.UpdateProductRequest,
    viewModel: ResultViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = req, block = {
        viewModel.updateProduct(req)
    })

    when (val state = viewModel.updateUiState.collectAsState().value) {
        is UiState.Loading -> {
            ShowLoading(controller, req)
        }

        is UiState.Failed -> {
            ShowFailed(controller, state.err, req, viewModel)
        }

        is UiState.Success -> {
            ShowSuccess(controller, req)
        }
        else -> {}
    }
}
