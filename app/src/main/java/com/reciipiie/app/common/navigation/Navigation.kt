package com.reciipiie.app.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.reciipiie.app.MainActivity
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.presantation.MainScreen
import com.reciipiie.app.presantation.SplashScreen
import com.reciipiie.app.presantation.viewmodel.RecipeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(
    parentNavController: NavHostController,
    mainActivity: MainActivity,
    childNavController: NavHostController,
) {
    val authViewModel = koinViewModel<AuthenticationViewModel>()
    val recipeViewModel = koinViewModel<RecipeViewModel>()
    NavHost(navController = parentNavController, startDestination = NavConstants.SPLASH_SCREEN) {
        composable(NavConstants.SPLASH_SCREEN) {
            SplashScreen(
                navigateToMain = {
                    parentNavController.navigate(NavConstants.MAIN_SCREEN) {
                        launchSingleTop = true
                        popUpTo(NavConstants.SPLASH_SCREEN) {
                            inclusive = true
                        }
                    }
                },

                navigateToLogin = {
                    parentNavController.navigate(NavConstants.LOGIN_SCREEN) {
                        launchSingleTop = true
                        popUpTo(NavConstants.SPLASH_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                authViewModel
            )
        }
        composable(NavConstants.MAIN_SCREEN) {
            MainScreen(
                childNavController = childNavController,
                parentNavController = parentNavController,
                authViewModel = authViewModel,
                recipeViewModel = recipeViewModel
            )
        }

    }

}