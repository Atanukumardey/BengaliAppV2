package com.appdevcourse.bengaliappv2.view.screens.authenticationScreens

import androidx.compose.foundation.*
//import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.view.navigation.NavigationRoute
import com.appdevcourse.bengaliappv2.screens.inputitems.*
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground


@Composable
fun LoginScreen(navController: NavHostController) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(WhiteBackground)
            .fillMaxSize()
    ) {

        item { headerImage(
            displayImage = R.drawable.register_new_image
        ) }

        item { introTextToShow(stringResource(R.string.sign_in)) }

        item {
            textInputField(
                value = username,
                onValueChange = { username = it },
                displayText = stringResource(id = R.string.user_name),
                icon = R.drawable.ic_user,
                maxLine = 5,
            )
        }
        item {
            textInputField(
                value = email,
                onValueChange = { email = it },
                displayText = stringResource(id = R.string.email_address),
                icon = R.drawable.ic_email_outline,
                keyboardType = KeyboardType.Email
            )
        }
        item {
            passwordInputField(
                value = password,
                onValueChange = { password = it },
                displayText = stringResource(id = R.string.password),
            )
        }

        item { Spacer(modifier = Modifier
            .padding(top = 20.dp)
        ) }
        item {
            submissionButton(
                displayText = stringResource(id = R.string.sign_in_with_email),
                onClick = {
                    navController.popBackStack()
                    navController.navigate(NavigationRoute.MainScreen.route)
                })
        }

        item { SocialMediaSignInButtons() }

        item {
            textLink(
                displayText = stringResource(id = R.string.do_not_have_account),
                onClick = {
//                    navController.popBackStack()
                    navController.navigate(NavigationRoute.SignUpScreen.route)
                }
            )
        }

        item {
            textLink(
                displayText = stringResource(id = R.string.forgot_password),
                onClick = {
//                    navController.popBackStack()
                    navController.navigate(NavigationRoute.ForgotPasswordScreen.route)
                }
            )
        }

    }


}

//@Composable
//fun giveRoute(navController: NavHostController) {
//    MainScreen(navController)
//}