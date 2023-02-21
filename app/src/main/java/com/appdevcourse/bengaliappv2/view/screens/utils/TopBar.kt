package com.appdevcourse.bengaliappv2.view.screens.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Composable
fun TopBar(coroutineScope: CoroutineScope = rememberCoroutineScope(), state: ScaffoldState = rememberScaffoldState()) {
    TopAppBar(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .clip(shape = RoundedCornerShape(16.dp)),
        title = { Text("Bengali") },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch { state.drawerState.open() }
            }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            // RowScope here, so these icons will be placed horizontally
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Share, contentDescription = "share")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "more")
            }
        },
        elevation = 10.dp
    )
}
