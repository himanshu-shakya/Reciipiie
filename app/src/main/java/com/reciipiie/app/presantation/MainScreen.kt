package com.reciipiie.app.presantation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.reciipiie.app.common.navigation.bottombar.BottomNavigationRoute
import com.reciipiie.app.common.navigation.bottombar.HomeBottomBarNavigation
import com.reciipiie.app.common.utils.NoRippleTheme
import com.reciipiie.app.presantation.viewmodel.AuthenticationViewModel
import com.reciipiie.app.presantation.viewmodel.RecipeViewModel
import com.reciipiie.app.ui.theme.Gray_0
import com.reciipiie.app.ui.theme.Gray_2
import com.reciipiie.app.ui.theme.Gray_6
import com.reciipiie.app.ui.theme.InterFontFamily
import com.reciipiie.app.ui.theme.Orange
import com.reciipiie.app.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    childNavController: NavHostController,
    parentNavController: NavHostController,
    authViewModel: AuthenticationViewModel,
    recipeViewModel: RecipeViewModel
) {
    val bottomNavItems = listOf(
        BottomNavigationRoute.Home(),
        BottomNavigationRoute.Favourite(),
    )
    val navBackStackEntry by childNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column{
                Divider(
                    color = Gray_2,
                    thickness = 1.dp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {

                    NavigationBar(
                        containerColor = Gray_0,

                        ) {
                        bottomNavItems.onEach { navData ->

                            NavigationBarItem(
                                selected = currentRoute == navData.route,
                                onClick = {
                                    childNavController.navigate(navData.route) {
                                        childNavController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    BadgedBox(badge = {
                                        androidx.compose.animation.AnimatedVisibility(
                                            visible = navData.hasBadge,
                                        ) {
                                            Badge()
                                        }
                                    }) {
                                        Icon(
                                            painter = painterResource(id = if (currentRoute == navData.route) navData.selectedIcon else navData.unselectedIcon),
                                            contentDescription = navData.title,
                                            modifier = Modifier.size(24.dp),
                                        )

                                    }
                                },
                                label = {
                                    Text(
                                        text = navData.title,
                                        fontFamily = InterFontFamily,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                    )
                                },
                                alwaysShowLabel = true  ,
                                colors = NavigationBarItemDefaults.colors(
                                    selectedTextColor = Orange,
                                    selectedIconColor = Orange,
                                    indicatorColor = White,
                                    unselectedTextColor = Gray_6,
                                    unselectedIconColor = Gray_6
                                ),

                                )
                        }
                    }
                }
            }


        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HomeBottomBarNavigation(
                childNavController = childNavController,
                parentNavController = parentNavController,
                authViewModel = authViewModel,
                recipeViewModel = recipeViewModel
            )
        }
    }
}