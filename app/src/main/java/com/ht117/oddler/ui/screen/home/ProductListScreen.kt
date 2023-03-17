package com.ht117.oddler.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ht117.data.model.AppErr
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.OddlerErrorText
import com.ht117.oddler.ui.component.ProductItem
import com.ht117.oddler.ui.theme.horizon
import com.ht117.oddler.ui.theme.vertical
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListRoute(
    modifier: Modifier,
    viewModel: ProductListViewModel = koinViewModel(),
    itemClick: (Product) -> Unit
) {
    when (val state = viewModel.uiState.collectAsStateWithLifecycle(initialValue = UiState.Loading).value) {
        is UiState.Loading -> ProductListLoading()
        is UiState.Failed -> ProductListFailed(err = state.err, viewModel)
        is UiState.Success -> ProductListResult(modifier = modifier, products = state.data, itemClick = itemClick)
    }
}

@Composable
internal fun ProductListLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun ProductListFailed(err: AppErr, viewModel: ProductListViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_failed),
            contentDescription = "",
            modifier = Modifier
                .width(96.dp)
                .height(96.dp)
                .clickable {
                    viewModel.refresh()
                }
        )
        Spacer(modifier = Modifier.height(vertical))
        OddlerErrorText(err = err, modifier = Modifier)
    }
}

@Composable
internal fun ProductListResult(modifier: Modifier, products: List<Product>, itemClick: (Product) -> Unit) {
    if (products.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = "",
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
            )
            Spacer(modifier = Modifier.height(vertical))
            Text(text = stringResource(id = R.string.product_empty))
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = horizon),
            verticalArrangement = Arrangement.Top
        ) {
            items(products.size) {
                val product = products[it]
                ProductItem(product = product, onItemClick = itemClick)
            }
        }
    }
}
