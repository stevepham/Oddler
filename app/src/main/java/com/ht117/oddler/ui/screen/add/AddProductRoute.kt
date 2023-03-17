package com.ht117.oddler.ui.screen.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.ht117.data.model.Request
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.OddlerButton
import com.ht117.oddler.ui.component.PercentItem
import com.ht117.oddler.ui.theme.horizon
import com.ht117.oddler.ui.theme.vertical
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductRoute(controller: NavHostController, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizon)
    ) {
        val (topItem, bottomItem) = createRefs()
        var txtName by remember {
            mutableStateOf("")
        }
        var txtPrice by remember {
            mutableStateOf("")
        }
        var txtDiscount by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .constrainAs(topItem) {
                    top.linkTo(parent.top, margin = vertical)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextField(
                value = txtName,
                label = { Text(text = stringResource(id = R.string.product_name)) },
                onValueChange = {
                    txtName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            TextField(
                value = txtPrice,
                label = { Text(text = stringResource(id = R.string.product_price)) },
                onValueChange = {
                    txtPrice = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = vertical),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                )
            )

            PercentItem(
                modifier = Modifier,
                percent = txtDiscount,
                isEditable = true,
                onValueChange = {
                    txtDiscount = it
                })
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
                isEnable = { true }, txtId = R.string.cancel
            )

            Spacer(modifier = Modifier.height(8.dp))

            OddlerButton(
                onClick = {
                    val discount = txtDiscount.toFloatOrNull() ?: 0F
                    val request = Request.AddProductRequest(txtName, txtPrice.toFloat(), discount)
                    val json = Json.encodeToString(request)
                    controller.navigate("products/result/add/$json")
                },
                isEnable = { txtPrice.isNotEmpty() && txtName.isNotEmpty() },
                txtId = R.string.add_product
            )

            Spacer(modifier = Modifier.height(vertical))
        }
    }
}
