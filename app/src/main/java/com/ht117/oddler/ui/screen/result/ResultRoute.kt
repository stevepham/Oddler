package com.ht117.oddler.ui.screen.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.ht117.data.model.AppErr
import com.ht117.data.model.Product
import com.ht117.data.model.Request
import com.ht117.oddler.R
import com.ht117.oddler.ui.component.OddlerButton
import com.ht117.oddler.ui.component.OddlerErrorText
import com.ht117.oddler.ui.screen.OddlerDestiny
import com.ht117.oddler.ui.screen.result.add.AddProductResult
import com.ht117.oddler.ui.screen.result.update.UpdateProductResult
import com.ht117.oddler.ui.theme.horizon
import com.ht117.oddler.ui.theme.maxVertical
import com.ht117.oddler.ui.theme.vertical
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val ACTION_ADD = "add"
private const val ACTION_UPDATE = "update"

@Composable
fun ResultRoute(controller: NavHostController, action: String, request: String, hasDelay: Boolean = false) {
    when (action) {
        ACTION_ADD -> {
            val req = Json.decodeFromString<Request.AddProductRequest>(request)
            AddProductResult(controller, req)
        }
        ACTION_UPDATE -> {
            val req = Json.decodeFromString<Request.UpdateProductRequest>(request)
            UpdateProductResult(controller, req)
        }
        else -> {
        }
    }
}

@Composable
internal fun ShowLoading(controller: NavHostController, req: Request) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizon)
    ) {
        val (loader, text, btnCancel) = createRefs()
        CircularProgressIndicator(
            modifier = Modifier
                .constrainAs(loader) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .width(64.dp)
                .height(64.dp)
        )

        Text(
            text = stringResource(
                id = if (req is Request.AddProductRequest) {
                    R.string.adding_product
                } else {
                    R.string.updating_product
                }
            ),
            modifier = Modifier.constrainAs(text) {
                top.linkTo(loader.bottom, margin = maxVertical)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center
        )

        OddlerButton(
            modifier = Modifier
                .constrainAs(btnCancel) {
                    bottom.linkTo(parent.bottom, margin = maxVertical)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            onClick = { controller.navigateUp() },
            isEnable = { true },
            txtId = R.string.cancel
        )
    }
}

@Composable
internal fun ShowFailed(controller: NavHostController, err: AppErr, req: Request, viewModel: ResultViewModel) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (icon, tvMsg, tvDetail, btnCancel, btnRetry) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .width(96.dp)
                .height(96.dp),
            painter = painterResource(id = R.drawable.ic_failed),
            contentDescription = ""
        )

        Text(
            text = stringResource(id = R.string.failed_to_add_product),
            modifier = Modifier.constrainAs(tvMsg) {
                top.linkTo(icon.bottom, margin = maxVertical)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center
        )

        OddlerErrorText(
            modifier = Modifier.constrainAs(tvDetail) {
                top.linkTo(tvMsg.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            err = err
        )

        OddlerButton(
            modifier = Modifier.constrainAs(btnRetry) {
                bottom.linkTo(btnCancel.top, margin = vertical)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = {
                if (req is Request.AddProductRequest) {
                    val json = Json.encodeToString(req)
                    controller.navigate("products/result/add/$json")
                } else if (req is Request.UpdateProductRequest) {
                    val json = Json.encodeToString(req)
                    controller.navigate("products/result/update/$json")
                }
            },
            isEnable = { true },
            txtId = R.string.retry
        )

        OddlerButton(
            modifier = Modifier.constrainAs(btnCancel) {
                bottom.linkTo(parent.bottom, margin = maxVertical)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = { controller.popBackStack() },
            isEnable = { true },
            txtId = R.string.cancel)
    }
}

@Composable
internal fun ShowSuccess(controller: NavHostController, req: Request) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizon)
    ) {
        val (icon, tvMsg, btnAddMore, btnUpdate, btnDone) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(icon) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .width(96.dp)
                .height(96.dp),
            painter = painterResource(id = R.drawable.ic_success),
            contentDescription = ""
        )

        Text(
            text = stringResource(
                id = if (req is Request.AddProductRequest) {
                    R.string.product_added
                } else {
                    R.string.product_update
                }
            ),
            modifier = Modifier.constrainAs(tvMsg) {
                top.linkTo(icon.bottom, margin = maxVertical)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center
        )

        if (req is Request.AddProductRequest) {
            OddlerButton(
                modifier = Modifier.constrainAs(btnAddMore) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(btnUpdate.top, margin = vertical)
                },
                onClick = {
                    controller.navigate(OddlerDestiny.AddDestiny.route) {
                        popUpTo(OddlerDestiny.AddDestiny.route)
                    }
                },
                isEnable = { true },
                txtId = R.string.add_more_product
            )

            OddlerButton(
                modifier = Modifier.constrainAs(btnUpdate) {
                    bottom.linkTo(btnDone.top, margin = vertical)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                onClick = {
                    val product = Product(req.name, req.price, req.discount)
                    val json = Json.encodeToString(product)
                    controller.navigate("products/$json/update")
                },
                isEnable = { true },
                txtId = R.string.adjust_discount
            )
        }

        OddlerButton(
            modifier = Modifier.constrainAs(btnDone) {
                bottom.linkTo(parent.bottom, margin = maxVertical)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = {
                controller.navigate(OddlerDestiny.HomeDestiny.route) {
                    popUpTo(OddlerDestiny.HomeDestiny.route)
                }
            },
            isEnable = { true },
            txtId = R.string.done
        )
    }
}
