package com.ht117.oddler.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ht117.data.model.AppErr
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.ErrorText
import com.ht117.oddler.ui.component.ProductItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListRoute(
    viewModel: ProductListViewModel = koinViewModel(),
    itemClick: (Product) -> Unit
) {
    when (val state = viewModel.uiState.collectAsStateWithLifecycle(initialValue = UiState.Loading).value) {
        is UiState.Loading -> ProductListLoading()
        is UiState.Failed -> ProductListFailed(err = state.err)
        is UiState.Success -> ProductListResult(products = state.data, itemClick = itemClick)
    }
}

@Composable
internal fun ProductListLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun ProductListFailed(err: AppErr) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Outlined.Warning, contentDescription = "")
        Spacer(modifier = Modifier.height(8.dp))
        ErrorText(err = err)
    }
}

@Composable
internal fun ProductListResult(products: List<Product>, itemClick: (Product) -> Unit) {
    if (products.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Info, contentDescription = "",
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(id = R.string.product_empty))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp, top = 56.dp),
            verticalArrangement = Arrangement.Top
        ) {
            items(products.size) {
                val product = products[it]
                ProductItem(product = product, onItemClick = itemClick)
            }
        }
    }
}
