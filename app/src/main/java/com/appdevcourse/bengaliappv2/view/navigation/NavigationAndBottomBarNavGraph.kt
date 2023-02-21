package com.appdevcourse.bengaliappv2.view.navigation

import android.content.ContentResolver
import android.content.res.AssetManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appdevcourse.bengaliappv2.view.screens.HomeScreen
import com.appdevcourse.bengaliappv2.view.screens.MiscPage
import com.appdevcourse.bengaliappv2.view.screens.ProfileScreen
import com.appdevcourse.bengaliappv2.view.screens.SettingsScreen
import com.appdevcourse.bengaliappv2.view.screens.reviewScreen.ReviewScreen

@Composable
fun NavigationAndBottomBarNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    miscActivity: () -> Unit,
    assetManager: AssetManager,
    contentresolver: ContentResolver
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HomeScreen.route
    ) {
        composable(route = NavigationRoute.HomeScreen.route) {
            HomeScreen(assetManager,contentresolver)
        }
        composable(route = NavigationRoute.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(route = NavigationRoute.SettingsScreen.route) {
            SettingsScreen()
        }
        composable(route = NavigationRoute.ReviewScreen.route){
            ReviewScreen()
        }
        composable(route = NavigationRoute.MiscPageScreen.route){
//            miscActivity()
//            MapsActivity()
            MiscPage(miscActivity)
        }

    }
}