package com.appdevcourse.bengaliappv2.view.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.appdevcourse.bengaliappv2.R


@Composable
fun RoundedImageHeader(
    imageUrl:String = "https://www.pngmart.com/files/21/Account-User-PNG-Clipart.png",
    userName:String = "User",
    userEmail:String = "abc@gmail.com"
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        Image(
//            painter = painterResource(id = R.drawable.pic_profile),
//            contentDescription = "profile pic",
//            modifier = Modifier
//                .clip(CircleShape)
//                .width(150.dp)
//                .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
//        )

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .width(150.dp)
                .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = userName, fontSize = 18.sp, color = Color.Black,)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = userEmail, fontSize = 14.sp, color = Color.Gray)

    }

}