package com.ht117.oddler.ui.screen.add

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
import com.ht117.oddler.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(controller: NavHostController, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
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
                    top.linkTo(parent.top, margin = 8.dp)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp)
        ) {
            TextField(
                value = txtName,
                label = { Text(text = "Product Name") },
                onValueChange = {
                    txtName = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            TextField(
                value = txtPrice,
                label = { Text(text = "Product Price") },
                onValueChange = {
                    txtPrice = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(top = 16.dp)
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
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.add_product))
            }
        }
    }
}