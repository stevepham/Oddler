package com.ht117.oddler.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ht117.data.model.AppErr
import com.ht117.data.model.Product
import com.ht117.data.model.UiState
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.OddlerErrorText
import com.ht117.oddler.ui.component.ProductItem
import com.ht117.oddler.ui.theme.horizon
import com.ht117.oddler.ui.theme.vertical
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductListRoute(
    controller: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = koinViewModel(),
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle(initialValue = ProductListState(UiState.Loading, DeleteProductState())).value

    when (state.productState) {
        is UiState.Loading -> ProductListLoading()
        is UiState.Failed -> ProductListFailed(err = state.productState.err, viewModel)
        is UiState.Success -> ProductListResult(
            controller = controller,
            modifier = modifier,
            viewModel = viewModel,
            productState = state.productState.data,
            deleteProductState = state.deleteProductState,
            itemClick = {
                val json = Json.encodeToString(it)
                controller.navigate("products/$json")
            }
        )
        else -> {}
    }
}

@Composable
internal fun ProductListLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
internal fun ProductListResult(
    controller: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel,
    productState: List<Product>,
    deleteProductState: DeleteProductState,
    itemClick: (Product) -> Unit
) {
    if (productState.isEmpty()) {
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
        var selectedProduct = remember {
            mutableStateOf<Product?>(null)
        }

        ShowProductAction(
            selectedProduct,
            onUpdateClick = {
                val json = Json.encodeToString(it)
                val route = "products/$json/update"
                controller.navigate(route)
            },
            onDeleteClick = {
                viewModel.deleteProduct(it)
            }
        )

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = horizon),
            verticalArrangement = Arrangement.Top
        ) {
            items(productState.size) {
                val product = productState[it]
                ProductItem(
                    product = product,
                    isDeleting = deleteProductState.product == product && deleteProductState.state is UiState.Loading,
                    onItemClick = itemClick,
                    actionClick = { prod ->
                        selectedProduct.value = prod
                    }
                )
            }
        }
    }
}

@Composable
internal fun ShowProductAction(
    selectedProduct: MutableState<Product?>,
    onUpdateClick: (Product) -> Unit,
    onDeleteClick: (Product) -> Unit
) {
    if (selectedProduct.value != null) {
        val local = LocalConfiguration.current

        Dialog(onDismissRequest = { selectedProduct.value = null }) {
            Card(
                modifier = Modifier
                    .width((local.screenWidthDp * 0.75F).dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                    text = "Product Actions"
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "You could choose update or delete product here",
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            onUpdateClick.invoke(selectedProduct.value!!)
                            selectedProduct.value = null
                        }
                    ) {
                        Text(text = "Update")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onDeleteClick.invoke(selectedProduct.value!!)
                            selectedProduct.value = null
                        }
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}
