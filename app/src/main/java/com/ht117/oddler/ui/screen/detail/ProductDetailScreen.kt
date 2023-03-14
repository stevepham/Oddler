package com.ht117.oddler.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ht117.data.model.Product
import com.ht117.data.model.getRawPrice
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.PercentItem
import com.ht117.oddler.ui.component.TextItem
import com.ht117.oddler.ui.screen.OddlerDestiny
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ProductDetailScreen(
    controller: NavHostController,
    modifier: Modifier,
    product: Product
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        val (topItem, bottomItem) = createRefs()

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
            Text(
                text = product.discount.toString(),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
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
                    controller.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 8.dp)
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }

            Button(
                onClick = {
                    val json = Json.encodeToString(product)
                    val route = "products/$json/update"

                    controller.navigate(route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.update))
            }
        }
    }
}
