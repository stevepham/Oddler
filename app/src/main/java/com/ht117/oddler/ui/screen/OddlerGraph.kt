package com.ht117.oddler.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ht117.data.model.Product
import com.ht117.oddler.R
import com.ht117.oddler.ui.screen.add.AddProductScreen
import com.ht117.oddler.ui.screen.detail.ProductDetailScreen
import com.ht117.oddler.ui.screen.home.ProductListRoute
import com.ht117.oddler.ui.screen.result.ResultScreen
import com.ht117.oddler.ui.screen.update.UpdateProductScreen
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class OddlerDestiny(open val route: String, open val titleId: Int) {
    object HomeDestiny : OddlerDestiny("products", R.string.products)
    object AddDestiny : OddlerDestiny("products/add", R.string.product)
    object DetailDestiny : OddlerDestiny("products/{detail}", R.string.product)
    object UpdateDestiny : OddlerDestiny("products/{detail}/update", R.string.adjust_discount)
    object ResultDestiny: OddlerDestiny("products/result", -1)
}

fun getCurrentDestiny(route: String?): OddlerDestiny? {
    return when (route) {
        OddlerDestiny.HomeDestiny.route -> OddlerDestiny.HomeDestiny
        OddlerDestiny.AddDestiny.route -> OddlerDestiny.AddDestiny
        OddlerDestiny.DetailDestiny.route -> OddlerDestiny.DetailDestiny
        OddlerDestiny.UpdateDestiny.route -> OddlerDestiny.UpdateDestiny
        OddlerDestiny.ResultDestiny.route -> OddlerDestiny.ResultDestiny
        else -> null
    }
}

@Composable
fun OddlerGraph(controller: NavHostController, modifier: Modifier) {
    NavHost(navController = controller, startDestination = OddlerDestiny.HomeDestiny.route) {
        composable(
            route = OddlerDestiny.HomeDestiny.route
        ) {
            ProductListRoute {
                val json = Json.encodeToString(it)
                controller.navigate("products/$json")
            }
        }
        composable(
            route = OddlerDestiny.DetailDestiny.route,
            arguments = listOf(
                navArgument("detail") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("detail")?.let {
                val product = Json.decodeFromString<Product>(it)
                ProductDetailScreen(modifier = modifier, controller = controller, product = product)
            }
        }
        composable(
            route = OddlerDestiny.UpdateDestiny.route,
            arguments = listOf(
                navArgument("detail") {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            entry.arguments?.getString("detail")?.let {
                val product = Json.decodeFromString<Product>(it)
                UpdateProductScreen(controller = controller, modifier = modifier, product = product)
            }
        }
        composable(
            route = OddlerDestiny.AddDestiny.route
        ) {
            AddProductScreen(controller, modifier)
        }
        composable(
            route = OddlerDestiny.ResultDestiny.route
        ) {
            ResultScreen(controller, modifier)
        }
    }
}
