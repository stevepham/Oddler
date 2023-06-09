package com.ht117.oddler.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.ht117.data.model.Product
import com.ht117.data.model.getDiscountedPrice
import com.ht117.data.model.getRawPrice

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(product: Product,
                isDeleting: Boolean = false,
                onItemClick: (Product) -> Unit,
                actionClick: (Product) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(64.dp),
        onClick = { onItemClick.invoke(product) }) {
        ConstraintLayout (
            modifier = Modifier.fillMaxSize()
        ) {
            val (tvName,
                tvPrice,
                tvDiscounted,
                btnAction,
                loader
            ) = createRefs()

            Text(
                modifier = Modifier
                    .constrainAs(tvName) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 8.dp),
                text = product.name,
                color = Color.Black
            )

            if (product.discount > 0) {
                Text(
                    text = product.getRawPrice(),
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier
                        .constrainAs(tvPrice) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(tvDiscounted.start)
                        }
                        .padding(end = 8.dp),
                    color = Color.Black
                )
                Text(
                    text = product.getDiscountedPrice(),
                    modifier = Modifier.constrainAs(tvDiscounted) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        if (isDeleting) {
                            end.linkTo(loader.start, 8.dp)
                        } else {
                            end.linkTo(btnAction.start, 8.dp)
                        }
                    },
                    color = Color.Black
                )
            } else {
                Text(
                    text = product.getRawPrice(),
                    modifier = Modifier
                        .constrainAs(tvPrice) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            if (isDeleting) {
                                end.linkTo(loader.start)
                            } else {
                                end.linkTo(btnAction.start)
                            }
                        }
                        .padding(end = 8.dp),
                    color = Color.Black
                )
            }

            if (isDeleting) {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(loader) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 8.dp)
                    }
                        .width(32.dp)
                        .height(32.dp)
                )
            } else {
                Button(
                    modifier = Modifier.constrainAs(btnAction) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 8.dp)
                    },
                    onClick = { actionClick.invoke(product) }
                ) {
                    Text(text = "Action")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProduct() {
    val products = listOf(
        Product("Shirt", 30F, 15F),
        Product("Shoes", 50F, 0F),
        Product("Skirt", 100F, 20F),
        Product("Pullower", 90F, 10F)
    )

//    LazyColumn {
//        items(products.size) {
//            ProductItem(product = products[it], onItemClick = {})
//        }
//    }
}
