package com.ht117.oddler.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.ht117.data.model.Product
import com.ht117.data.model.getRawPrice
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.OddlerButton
import com.ht117.oddler.ui.component.PercentItem
import com.ht117.oddler.ui.component.TextItem
import com.ht117.oddler.ui.theme.horizon
import com.ht117.oddler.ui.theme.maxVertical
import com.ht117.oddler.ui.theme.vertical
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ProductDetailRoute(
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
                percent = product.discount.toString(),
                isEditable = false,
                onValueChange = {}
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
                onClick = {
                    controller.navigateUp()
                },
                isEnable = { true },
                txtId = R.string.cancel
            )

            Spacer(modifier = Modifier.height(vertical))

            OddlerButton(
                onClick = {
                    val json = Json.encodeToString(product)
                    val route = "products/$json/update"

                    controller.navigate(route)
                },
                isEnable = { true },
                txtId = R.string.update
            )

            Spacer(modifier = Modifier.height(maxVertical))
        }
    }
}
