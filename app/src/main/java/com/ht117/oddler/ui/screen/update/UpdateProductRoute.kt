package com.ht117.oddler.ui.screen.update

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.ht117.data.model.Product
import com.ht117.data.model.Request
import com.ht117.data.model.getRawPrice
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.TextItem
import com.ht117.oddler.ui.screen.OddlerDestiny
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductRoute(controller: NavHostController, modifier: Modifier, product: Product) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        val (topItem, bottomItem) = createRefs()
        var txtDiscount by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .constrainAs(topItem) {
                    top.linkTo(parent.top, margin = 8.dp)
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = txtDiscount,
                    onValueChange = {
                        txtDiscount = it
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth()
                        .padding(end = 48.dp)
                        .background(Color.Gray)
                )

                Box(
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight()
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .align(Alignment.CenterEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "%", color = Color.White)
                }
            }
        }

        Column(
            modifier = Modifier
                .constrainAs(bottomItem) {
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Button(
                onClick = {
                    controller.navigate(OddlerDestiny.HomeDestiny.route) {
                        popUpTo(OddlerDestiny.HomeDestiny.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val discount = txtDiscount.toFloatOrNull()?: 0F

                    if (discount != product.discount) {
                        val req = Request.UpdateProductRequest(product.name, discount)
                        val json = Json.encodeToString(req)
                        controller.navigate("products/result/update/$json")
                    } else {
                        // Show error message
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = stringResource(id = R.string.update))
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
