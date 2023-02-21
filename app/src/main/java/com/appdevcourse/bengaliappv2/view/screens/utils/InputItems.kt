package com.appdevcourse.bengaliappv2.screens.inputitems


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.ui.theme.BackgroundColor
import com.appdevcourse.bengaliappv2.ui.theme.InputBoxShape
import com.appdevcourse.bengaliappv2.ui.theme.SecondaryColor
import com.appdevcourse.bengaliappv2.ui.theme.Shapes


@Composable
fun headerImage(
    displayImage: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp))
    ) {
        Image(
            painter = painterResource(id = displayImage),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        )
    }
}


@Composable
fun introTextToShow(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
//            fontFamily = Poppins,
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 14.dp),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 16.sp,

        )
}

@Composable
fun textInputField(
    value: String,
    onValueChange: (String) -> Unit,
    displayText: String,
    icon: Int = -1,
    iconVector: ImageVector = Icons.Outlined.Home,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLine: Int = 1,
    imeAction:ImeAction = ImeAction.Done,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .padding(top = 2.dp)
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        maxLines = maxLine,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.primary,
            backgroundColor = Color.White,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = InputBoxShape.medium,
        singleLine =  singleLine,
        leadingIcon = {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (icon > -1) {
                    Icon(
                        painter = painterResource(id = icon), contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Icon(
                        imageVector = iconVector, contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(
                    modifier = Modifier
                        .width(6.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(BackgroundColor)
                )
            }
        },
        label = {
            Text(
                text = displayText,
                color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
            )
        },
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
//                fontFamily = Poppins
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
    )

}


@Composable
fun passwordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    displayText: String,
) {
    var isPasswordOpen by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 2.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.primary,
            backgroundColor = Color.White,
            cursorColor = MaterialTheme.colors.primary,
            focusedIndicatorColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = InputBoxShape.medium,
        singleLine = true,
        leadingIcon = {
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock), contentDescription = "",
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(6.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(BackgroundColor)
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordOpen = !isPasswordOpen }) {
                if (!isPasswordOpen) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye_open),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eye_close),
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        label = {
            Text(
                text = displayText,
                color = MaterialTheme.colors.primary.copy(alpha = 0.5f),
            )
        },
        visualTransformation = if (!isPasswordOpen) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
//                fontFamily = Poppins
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        )
    )
}


@Composable
fun checkBoxItems(
    checkBoxChecked: Boolean,
    displayText: String,
    onCheckedChanged: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkBoxChecked, onCheckedChange = onCheckedChanged,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
                uncheckedColor = MaterialTheme.colors.primary,
                checkmarkColor = Color.White
            ),
            modifier = Modifier.clip(shape = Shapes.medium)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = displayText,
//                fontFamily = Poppins,
            color = Color.Black,
            fontSize = 12.sp
        )
    }
}


@Composable
fun submissionButton(
    displayText: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp),
//                .clip(RoundedCornerShape(15.dp)),
        shape = InputBoxShape.medium,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 2.dp
        ),
    ) {
        Text(
            text = displayText,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun textLink(
    displayText: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
    ) {
        Text(
            text = displayText,
//                fontFamily = Poppins,
            color = SecondaryColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
fun SocialMediaSignInButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SocialMediaButton(
            buttonText = "Google",
            icon = R.drawable.ic_google,
            onClick = {

            }
        )
        SocialMediaButton(
            buttonText = "Facebook",
            icon = R.drawable.ic_facebook,
            onClick = {

            }
        )
    }
}


@Composable
fun SocialMediaButton(
    buttonText: String,
    onClick: () -> Unit,
    icon: Int
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.3f),
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        contentPadding = PaddingValues(horizontal = 26.dp, vertical = 10.dp),
//        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        shape = InputBoxShape.medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = buttonText,
//                    fontFamily = Poppins,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
