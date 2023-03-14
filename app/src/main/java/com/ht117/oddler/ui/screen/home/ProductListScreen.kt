package com.ht117.oddler.ui.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ht117.data.model.Product
import com.ht117.oddler.ui.component.ProductItem

@Composable
fun ProductListScreen(
    controller: NavHostController,
    modifier: Modifier,
    itemClick: (Product) -> Unit
) {
    val products = listOf(
        Product("A", 30F, 10F),
        Product("B", 50F, 20F),
        Product("C", 90F, 0F),
        Product("D", 120F, 10F),
        Product("A", 30F, 10F),
        Product("B", 50F, 20F),
        Product("C", 90F, 0F),
        Product("D", 120F, 10F),
        Product("A", 30F, 10F),
        Product("B", 50F, 20F),
        Product("C", 90F, 0F),
        Product("A", 30F, 10F),
        Product("B", 50F, 20F),
        Product("C", 90F, 0F)
    )
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        items(products.size) {
            val product = products[it]
            ProductItem(product = product, onItemClick = { itemClick.invoke(product) })
        }
    }
}
