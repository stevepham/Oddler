package com.ht117.oddler.ui.screen.update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.ht117.data.model.Product
import com.ht117.data.model.Request
import com.ht117.data.model.getRawPrice
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.OddlerButton
import com.ht117.oddler.ui.component.PercentItem
import com.ht117.oddler.ui.component.TextItem
import com.ht117.oddler.ui.screen.OddlerDestiny
import com.ht117.oddler.ui.theme.horizon
import com.ht117.oddler.ui.theme.maxVertical
import com.ht117.oddler.ui.theme.vertical
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun UpdateProductRoute(
    controller: NavHostController,
    modifier: Modifier,
    product: Product
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizon)
    ) {
        val (topItem, bottomItem) = createRefs()
        var txtDiscount by remember {
            mutableStateOf(product.discount.toString())
        }

        Column(
            modifier = Modifier
                .constrainAs(topItem) {
                    top.linkTo(parent.top, margin = vertical)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = product.name,
                isEnable = false
            )

            TextItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = product.getRawPrice(),
                isEnable = false
            )

            PercentItem(
                modifier = Modifier,
                percent = txtDiscount,
                isEditable = true,
                onValueChange = { txtDiscount = it }
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(bottomItem) {
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            OddlerButton(
                txtId = R.string.cancel,
                onClick = {
                    controller.navigate(OddlerDestiny.HomeDestiny.route) {
                        popUpTo(OddlerDestiny.HomeDestiny.route)
                    }
                },
                isEnable = { true }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OddlerButton(
                onClick = {
                    val discount = txtDiscount.toFloatOrNull() ?: 0F
                    val req = Request.UpdateProductRequest(product.name, discount)
                    val json = Json.encodeToString(req)
                    controller.navigate("products/result/update/$json")
                }, isEnable = {
                    txtDiscount.toFloatOrNull() != product.discount
                }, txtId = R.string.update
            )

            Spacer(modifier = Modifier.height(maxVertical))
        }
    }
}
