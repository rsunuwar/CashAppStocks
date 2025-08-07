package com.example.cashappstocks.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cashappstocks.ui.screens.AboutScreen
import com.example.cashappstocks.ui.screens.DetailsScreen
import com.example.cashappstocks.ui.screens.StocksListScreen
import com.example.cashappstocks.ui.screens.StocksListViewModel
import com.example.cashappstocks.ui.screens.StocksScreensName

@Composable
fun CashAppStocksNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController,
        startDestination = StocksScreensName.MainScreen.name
    ) {

        composable(route = StocksScreensName.MainScreen.name) {
            val stocksVM = hiltViewModel<StocksListViewModel>()
            StocksListScreen(
                viewModel = stocksVM,
                onAboutClicked = {
                    navController.navigate(StocksScreensName.AboutScreen.name) },
                onStockRowClicked = { name, priceInDollars ->
                    navController.navigate(route = "${StocksScreensName.DetailsScreen.name}/$name/$priceInDollars")
                })
        }

        composable(route = StocksScreensName.AboutScreen.name) {
            AboutScreen { navController.popBackStack() }
        }

        composable(route = "${StocksScreensName.DetailsScreen.name}/{name}/{priceInDollars}",
            arguments = listOf(
                navArgument(name = "name") {
                    type = NavType.StringType
                },
                navArgument(name = "priceInDollars") {
                    type = NavType.FloatType
                }
            )
        ) { navBackStack ->

            val name = navBackStack.arguments?.getString("name")
            val priceInDollars = navBackStack.arguments?.getFloat("priceInDollars")
            DetailsScreen (
                name = name,
                priceInDollars = priceInDollars
            ) {
                navController.popBackStack()
            }
        }
    }
}
