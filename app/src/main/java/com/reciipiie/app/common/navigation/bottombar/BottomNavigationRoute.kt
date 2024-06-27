package com.reciipiie.app.common.navigation.bottombar

import com.reciipiie.app.R
import com.reciipiie.app.common.navigation.NavConstants


sealed class BottomNavigationRoute(
    var route: String,
    var title: String,
    var unselectedIcon: Int,
    var selectedIcon: Int,
    var hasBadge: Boolean = false
) {
    data class Home(var isBadgeEnabled: Boolean = false) : BottomNavigationRoute(
        NavConstants.HOME_SCREEN,
        "Home",
        R.drawable.ic_unselected_house,
        R.drawable.ic_selected_house,
        isBadgeEnabled

    )
    data class Favourite(var isBadgeEnabled: Boolean = false) : BottomNavigationRoute(
        NavConstants.FAVOURITE_SCREEN,
        "Favourite",
        R.drawable.ic_unselected_favourite,
        R.drawable.ic_selected_favourite,
        isBadgeEnabled

    )

}