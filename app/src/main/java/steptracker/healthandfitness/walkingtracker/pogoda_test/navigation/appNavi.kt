package steptracker.healthandfitness.walkingtracker.pogoda_test.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import steptracker.healthandfitness.walkingtracker.pogoda_test.data.MainViewModel
import steptracker.healthandfitness.walkingtracker.pogoda_test.screens.setting
import steptracker.healthandfitness.walkingtracker.pogoda_test.screens.weather

@Composable
fun AppNavi(viewModel: MainViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "weather"
    ) {
        composable("weather") {
            weather(viewModel,navController)
        }
        composable("settings") {
            setting(navController, viewModel)
        }
    }
}