package com.appdevcourse.bengaliappv2

import android.content.ContentResolver
import android.content.res.AssetManager
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.appdevcourse.bengaliappv2.view.navigation.NavigationAndBottomBarNavGraph
import com.appdevcourse.bengaliappv2.view.screens.utils.TopBar
import com.appdevcourse.bengaliappv2.view.screens.utils.bottombar.BottomBar
import com.appdevcourse.bengaliappv2.view.screens.utils.navigationbar.NavigationBar
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground


@Composable
fun MainScreen(mng: AssetManager, contentresolver: ContentResolver, activitySwap: () -> Unit) {
    val navController = rememberNavController()
    val state = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
//    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
//    val selectedItem = remember { mutableStateOf(items[0]) }
Surface() {
    Scaffold(
        scaffoldState = state,
        topBar = { TopBar(coroutineScope, state) },
        bottomBar = {
            BottomBar(navController = navController)
        },
        drawerContent = { NavigationBar(state, coroutineScope, navController) },
        drawerBackgroundColor = WhiteBackground.copy(alpha = .7f),
        drawerElevation = 10.dp,
        drawerShape = RoundedCornerShape(30.dp),
        content =  {
            NavigationAndBottomBarNavGraph(
                modifier = Modifier.padding(it),
                navController = navController,
                miscActivity = activitySwap,
                contentresolver = contentresolver,
                assetManager = mng)
        }
    )
}

}












