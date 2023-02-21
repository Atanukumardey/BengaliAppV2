package com.appdevcourse.bengaliappv2.view.screens

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.appdevcourse.bengaliappv2.R
import com.appdevcourse.bengaliappv2.ui.theme.WhiteBackground
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


@Composable
fun ComposeMapCenterPointMapMarker() {
    val markerPosition = LatLng(22.46976005288001, 91.79490946233273)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerPosition, 18f)
    }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ){
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
            Text(text = "Is Surfing: ${cameraPositionState.isMoving}" +
                    "\n Latitude and Longitude: ${cameraPositionState.position.target.latitude} " +
                    "and ${cameraPositionState.position.target.longitude}",
                textAlign = TextAlign.Center
            )
        }
    }

}



@Composable
fun WebViewPage(url: String){

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.userAgentString = System.getProperty("http.agent")
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })

}

@Composable
fun YouTubeVideo(){
    AndroidView(
        factory = {
            View.inflate(it,R.layout.youtubevideoview, null)
                  },
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        update = {
            val youTubePlayerView: YouTubePlayerView = it.findViewById(R.id.youtube_player_view)
           // getLifecycle().addObserver(youTubePlayerView)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = "54BsUsKn69U"
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        }
    )

}

@Composable
fun MiscPage( activitySwap: () -> Unit){
//    val navigate = Intent(activity, SecondActivity::class.java);
//    startActivity(navigate)
    Box(
        modifier = Modifier
            .background(WhiteBackground)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Text(text = "Map", fontWeight = FontWeight.Bold)
            ComposeMapCenterPointMapMarker()
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Youtube Video", fontWeight = FontWeight.Bold)
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)){
//                WebViewPage("https://www.youtube.com/watch?v=qYhvEJsWH4M")
                YouTubeVideo()
            }
        }
    }
}