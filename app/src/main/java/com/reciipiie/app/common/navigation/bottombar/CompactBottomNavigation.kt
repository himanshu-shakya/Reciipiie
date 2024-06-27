package com.reciipiie.app.common.navigation.bottombar

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reciipiie.app.presantation.FavouriteScreen
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.presantation.viewmodel.RecipeViewModel
import com.reciipiie.app.presentation.HomeScreen

@Composable
fun HomeBottomBarNavigation(
    childNavController: NavHostController,
    parentNavController: NavController,
    authViewModel: AuthenticationViewModel,
    recipeViewModel: RecipeViewModel,
) {

    NavHost(
        navController = childNavController,
        startDestination = BottomNavigationRoute.Home().route
    ) {
        composable(BottomNavigationRoute.Home().route) {
            HomeScreen(
                authViewModel = authViewModel,
                recipeViewModel = recipeViewModel
            )
        }
        composable(BottomNavigationRoute.Favourite().route) {
            FavouriteScreen()
        }

    }

}