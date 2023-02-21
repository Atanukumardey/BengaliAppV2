package com.appdevcourse.bengaliappv2.view.navigation


sealed class NavigationRoute(
    val route: String,
) {
    object LoginScreen: NavigationRoute(
        route = "loginScreen"
    )

    object MainScreen: NavigationRoute(
        route = "mainScreen"
    )

    object HomeScreen : NavigationRoute(
        route = "homeScreen"
    )

    object ProfileScreen : NavigationRoute(
        route = "profileScreen"
    )

    object SettingsScreen : NavigationRoute(
        route = "settingsScreen"
    )

    object ReviewScreen: NavigationRoute(
        route = "reviewScreen"
    )

    object SignUpScreen: NavigationRoute(
        route = "SignUpScreen"
    )
    object ForgotPasswordScreen: NavigationRoute(
        route = "ForgotPasswordScreen"
    )
    object MiscPageScreen: NavigationRoute(
        route = "MiscPageScreen"
    )

}
