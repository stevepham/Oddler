package com.ht117.oddler.ui.screen.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ht117.data.model.Request
import com.ht117.data.model.UiState
import com.ht117.oddler.R
import com.ht117.oddler.ui.screen.OddlerDestiny
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResultRoute(controller: NavHostController, modifier: Modifier, action: String, request: String) {

    when (action) {
        "add" -> {
            val req = Json.decodeFromString<Request.AddProductRequest>(request)
            AddProductResult(controller, req)
        }
        "update" -> {
            val req = Json.decodeFromString<Request.UpdateProductRequest>(request)
            UpdateProductResult(controller, req)
        }
        else -> {
        }
    }
}

@Composable
fun AddProductResult(controller: NavHostController, req: Request.AddProductRequest, viewModel: ResultViewModel = koinViewModel()) {
    LaunchedEffect(key1 = req, block = {
        viewModel.addProduct(req)
    })

    when (viewModel.addUiState.collectAsState().value) {
        is UiState.Loading -> {
            ShowLoading(controller)
        }
        is UiState.Failed -> {
            ShowFailed(controller)
        }
        is UiState.Success -> {
            ShowSuccess(controller)
        }
    }
}

@Composable
fun UpdateProductResult(controller: NavHostController, req: Request.UpdateProductRequest, viewModel: ResultViewModel = koinViewModel()) {
    LaunchedEffect(key1 = req, block = {
        viewModel.updateProduct(req)
    })

    when (viewModel.updateUiState.collectAsState().value) {
        is UiState.Loading -> {
            ShowLoading(controller = controller)
        }
        is UiState.Failed -> {
            ShowFailed(controller)
        }
        is UiState.Success -> {
            ShowSuccess(controller)
        }
    }
}

@Composable
fun ShowLoading(controller: NavHostController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
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
            text = stringResource(id = R.string.adding_product),
            modifier = Modifier.constrainAs(text) {
                top.linkTo(loader.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                controller.navigateUp()
            }, modifier = Modifier
                .constrainAs(btnCancel) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(64.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
            Text(text = stringResource(id = R.string.cancel))
        }
    }
}

@Composable
fun ShowFailed(controller: NavHostController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
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
                top.linkTo(icon.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center
        )

        Text(
            text = "Reason",
            modifier = Modifier
                .constrainAs(tvDetail) {
                    top.linkTo(tvMsg.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(top = 8.dp)
        )

        Button(
            onClick = {

            }, modifier = Modifier
                .constrainAs(btnRetry) {
                    bottom.linkTo(btnCancel.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.retry))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

            }, modifier = Modifier
                .constrainAs(btnCancel) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
            Text(text = stringResource(id = R.string.cancel))
        }
    }
}

@Composable
fun ShowSuccess(controller: NavHostController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
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
            text = stringResource(id = R.string.product_added),
            modifier = Modifier.constrainAs(tvMsg) {
                top.linkTo(icon.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            textAlign = TextAlign.Center
        )

        Button(
            onClick = {
                controller.navigate(OddlerDestiny.AddDestiny.route)
            },
            modifier = Modifier
                .constrainAs(btnAddMore) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(btnUpdate.top)
                }
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.add_more_product))
        }

        Button(
            onClick = {

            },
            modifier = Modifier
                .constrainAs(btnUpdate) {
                    bottom.linkTo(btnDone.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Text(text = stringResource(id = R.string.adjust_discount))
        }

        Button(
            onClick = {
                controller.navigateUp()
            },
            modifier = Modifier
                .constrainAs(btnDone) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
            Text(text = stringResource(id = R.string.done))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowLoading() {
    ShowFailed(controller = rememberNavController())
//    ShowSuccess(rememberNavController())
}
