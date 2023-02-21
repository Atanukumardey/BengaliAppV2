package com.appdevcourse.bengaliappv2.view.screens.authenticationScreens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.view.navigation.NavigationRoute
import com.appdevcourse.bengaliappv2.screens.inputitems.headerImage
import com.appdevcourse.bengaliappv2.screens.inputitems.introTextToShow
import com.appdevcourse.bengaliappv2.screens.inputitems.submissionButton
import com.appdevcourse.bengaliappv2.screens.inputitems.textInputField
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground


@Composable
fun ForgotPasswordScreen(navController: NavHostController) {

    var email by remember { mutableStateOf("") }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .background(WhiteBackground)
            .fillMaxSize()
            .padding(bottom = 5.dp)
    ) {

        item { headerImage(
            displayImage = R.drawable.register_new_image
        ) }

        item {
            Spacer(modifier = Modifier
                .padding(top = 30.dp, bottom = 30.dp)
            )
        }

        item { introTextToShow(stringResource(id = R.string.enter_your_email)) }

        item {
            Spacer(modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
            )
        }
        item {
            textInputField(
                value = "",
                onValueChange = { email = it },
                displayText = stringResource(id = R.string.email_address),
                icon = R.drawable.ic_email_outline
            )
        }

        item {
            Spacer(modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
            )
        }

        item {
            submissionButton(
                displayText = stringResource(id = R.string.recover_password),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(NavigationRoute.LoginScreen.route)
                })
        }

    }

}

//@Composable
//fun giveRoute(navController: NavHostController) {
//    MainScreen(navController)
//}

