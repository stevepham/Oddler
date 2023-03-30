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
import com.ht117.oddler.ui.screen.add.AddProductRoute
import com.ht117.oddler.ui.screen.detail.ProductDetailRoute
import com.ht117.oddler.ui.screen.home.ProductListRoute
import com.ht117.oddler.ui.screen.result.ResultRoute
import com.ht117.oddler.ui.screen.update.UpdateProductRoute
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class OddlerDestiny(open val route: String, open val titleId: Int) {
    object HomeDestiny : OddlerDestiny("products", R.string.products)
    object AddDestiny : OddlerDestiny("products/add", R.string.product)
    object DetailDestiny : OddlerDestiny("products/{detail}", R.string.product)
    object UpdateDestiny : OddlerDestiny("products/{detail}/update", R.string.adjust_discount)
    object ResultDestiny: OddlerDestiny("products/result/{action}/{request}", -1)
}

fun getCurrentDestiny(route: String?): OddlerDestiny? {
    return when (route) {
        OddlerDestiny.HomeDestiny.route -> OddlerDestiny.HomeDestiny
        OddlerDestiny.AddDestiny.route -> OddlerDestiny.AddDestiny
        OddlerDestiny.DetailDestiny.route -> OddlerDestiny.DetailDestiny
        OddlerDestiny.UpdateDestiny.route -> OddlerDestiny.UpdateDestiny
        else -> null
    }
}

@Composable
fun OddlerGraph(controller: NavHostController, modifier: Modifier) {
    NavHost(navController = controller, startDestination = OddlerDestiny.HomeDestiny.route) {
        composable(
            route = OddlerDestiny.HomeDestiny.route
        ) {
            ProductListRoute(controller, modifier)
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
                ProductDetailRoute(modifier = modifier, controller = controller, product = product)
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
                UpdateProductRoute(controller = controller, modifier = modifier, product = product)
            }
        }
        composable(
            route = OddlerDestiny.AddDestiny.route
        ) {
            AddProductRoute(controller, modifier)
        }
        composable(
            route = OddlerDestiny.ResultDestiny.route,
            arguments = listOf(
                navArgument("action") { type = NavType.StringType },
                navArgument("request") { type = NavType.StringType }
            )
        ) { entry ->
            val action = entry.arguments?.getString("action")?: ""
            val content = entry.arguments?.getString("request").orEmpty()

            ResultRoute(controller = controller, action, content)
        }
    }
}
