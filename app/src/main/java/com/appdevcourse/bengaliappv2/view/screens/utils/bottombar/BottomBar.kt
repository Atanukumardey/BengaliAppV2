package com.appdevcourse.bengaliappv2.view.screens.utils.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.appdevcourse.bengaliappv2.view.screens.utils.NavigationItems
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationItems.Profile,
        NavigationItems.Home,
        NavigationItems.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        ) {
            Row(
                modifier = Modifier
                    .background(WhiteBackground)
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                screens.forEach { screen ->
                    CustomBottomAddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    }
}





@Composable
fun CustomBottomAddItem(
    screen: NavigationItems,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true;
    val background =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.3f) else Color.Transparent
    val contentColor =
        if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                tint = contentColor,
            )

            AnimatedVisibility(
                visible = isSelected
            ) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }

        }
    }

}


//@Composable
//fun RowScope.AddItem(
//    screen: BottomBarItems,
//    currentDestination: NavDestination?,
//    navController: NavHostController
//) {
//    BottomNavigationItem(
//        label = {
//            Text(text = screen.title)
//        },
//        icon = {
//            Icon(
//                imageVector = screen.icon,
//                contentDescription = "Navigation Icon"
//            )
//        },
//        selected = currentDestination?.hierarchy?.any {
//            it.route == screen.route
//        } == true,
//        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
//        onClick = {
//            navController.navigate(screen.route) {
//                popUpTo(navController.graph.findStartDestination().id)
//                launchSingleTop = true
//            }
//        }
//    )
//}

