package com.appdevcourse.bengaliappv2.view.screens.utils


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.appdevcourse.bengaliappv2.view.navigation.NavigationRoute


sealed class NavigationItems
    (
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Profile : NavigationItems(
        route = NavigationRoute.ProfileScreen.route,
        title = "Profile",
        icon = Icons.Outlined.Person
    )

    object Home : NavigationItems(
        route = NavigationRoute.HomeScreen.route,
        title = "Home",
        icon = Icons.Outlined.Home
    )

    object Settings : NavigationItems(
        route = NavigationRoute.SettingsScreen.route,
        title = "Settings",
        icon = Icons.Outlined.Settings
    )

    object Review: NavigationItems(
        route = NavigationRoute.ReviewScreen.route,
        title = "Review",
        icon = Icons.Outlined.Reviews
    )

    object MiscPage: NavigationItems(
        route = NavigationRoute.MiscPageScreen.route,
        title = "Misc",
        icon = Icons.Outlined.Map
    )
}
