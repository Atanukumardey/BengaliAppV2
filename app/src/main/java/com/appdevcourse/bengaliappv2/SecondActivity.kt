package com.appdevcourse.bengaliappv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.appdevcourse.bengaliappv2.ui.theme.BengaliAppV2Theme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

//
//@Composable
//fun MiscPage(){
//    val singapore = LatLng(1.35, 103.87)
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(singapore, 10f)
//    }
//    Log.e("Here","problem")
//    Box(
//        modifier = Modifier
//            .background(Color.White)
//            .fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
////        Column(
////            modifier = Modifier
////                .fillMaxWidth(),
////            verticalArrangement = Arrangement.Center,
////            horizontalAlignment = Alignment.CenterHorizontally
////        ) {
//        GoogleMap(
//            modifier = Modifier.fillMaxSize(.8f),
//            cameraPositionState = cameraPositionState
//        ) {
//            Marker(
//                state = MarkerState(position = singapore),
//                title = "Singapore",
//                snippet = "Marker in Singapore"
//            )
//        }
//        //Text(text = "Helllo")
//
////        }
//    }
//}
@Composable
fun ComposeMapCenterPointMapMarker() {
    val markerPosition = LatLng(22.46976005288001, 91.79490946233273)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 18f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(.8f),
        cameraPositionState = cameraPositionState
    )
    Column(
        modifier = Modifier.fillMaxSize(.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_about),
                contentDescription = "marker",
            )
        }
        Text(text = "Is camera moving: ${cameraPositionState.isMoving}" +
                "\n Latitude and Longitude: ${cameraPositionState.position.target.latitude} " +
                "and ${cameraPositionState.position.target.longitude}",
            textAlign = TextAlign.Center
        )
    }
}


class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BengaliAppV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ComposeMapCenterPointMapMarker()
                }

            }
        }
    }
}