package com.appdevcourse.bengaliappv2.view.screens.utils.navigationbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import com.appdevcourse.bengaliappv2.view.screens.utils.NavigationItems
import com.appdevcourse.bengaliappv2.view.screens.utils.RoundedImageHeader
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun NavigationBar(
    state: ScaffoldState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val screens = listOf(
        NavigationItems.Home,
        NavigationItems.Profile,
        NavigationItems.Settings,
        NavigationItems.Review,
        NavigationItems.MiscPage
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        LazyColumn(
            modifier = Modifier
                .background(color = WhiteBackground.copy(alpha = .8f))
                .fillMaxSize()
//                .padding(8.dp),
        ) {
//            Text(
//                text = "Google Classroom",
//                style = TextStyle(color = Color.Black, fontSize = 24.sp),
//                modifier = Modifier.padding(12.dp)
//            )
            item { RoundedImageHeader() }
//            item { Spacer(
//                modifier = Modifier
//                    .padding(top = 8.dp, bottom = 8.dp)
//            ) }
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .height(.5.dp),
                    thickness = 10.dp,
                    color = MaterialTheme.colors.primary
                )
            }
            screens.forEach { screen ->
                item {
                    DrawerItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                            coroutineScope.launch { state.drawerState.close() }
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun DrawerItem(
    screen: NavigationItems,
    currentDestination: NavDestination?,
    onClick: () -> Unit
) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    val background =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.3f) else Color.Transparent
    val contentColor =
        if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                modifier = Modifier.padding(8.dp),
                tint = contentColor,
            )
            Text(
                text = screen.title,
                style = TextStyle(color = contentColor, fontSize = 17.sp),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


//@Preview
//@Composable
//fun NavigationBarPreview(){
//    val navController = rememberNavController()
//    val state  = rememberScaffoldState()
//    val coroutineScope = rememberCoroutineScope()
//
//    BengaliAppTheme {
//        Scaffold(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.White)
//        ) {
//            NavigationBar(state, coroutineScope, navController)
//        }
//    }
//}

