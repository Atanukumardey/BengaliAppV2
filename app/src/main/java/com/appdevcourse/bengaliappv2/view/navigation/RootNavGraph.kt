package com.appdevcourse.bengaliappv2.view.navigation

import android.content.ContentResolver
import android.content.res.AssetManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.appdevcourse.bengaliappv2.MainScreen
import com.appdevcourse.bengaliappv2.view.screens.authenticationScreens.ForgotPasswordScreen
import com.appdevcourse.bengaliappv2.view.screens.authenticationScreens.LoginScreen
import com.appdevcourse.bengaliappv2.view.screens.authenticationScreens.SignUpScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    mng: AssetManager,
    contentresolver: ContentResolver,
    activitySwap: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.LoginScreen.route
    ) {
        composable(route = NavigationRoute.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(route = NavigationRoute.MainScreen.route){
            MainScreen(mng,contentresolver, activitySwap)
        }
        composable(route = NavigationRoute.SignUpScreen.route){
            SignUpScreen(navController)
        }
        composable(route = NavigationRoute.ForgotPasswordScreen.route){
            ForgotPasswordScreen(navController)
        }
    }
}