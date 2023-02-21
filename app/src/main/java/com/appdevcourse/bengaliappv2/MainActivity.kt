package com.appdevcourse.bengaliappv2

//import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.compose.rememberNavController
import com.appdevcourse.bengaliappv2.view.navigation.RootNavigationGraph
import com.appdevcourse.bengaliappv2.ui.theme.BengaliAppV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mng: AssetManager = assets
        var contentresolver = contentResolver

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)){
                view, insets ->
            val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            view.updatePadding(bottom=bottom)
            insets
        }
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BengaliAppV2Theme {
//                RequestMultiplePermissions( permissions = listOf(Manifest.permission_group.STORAGE, Manifest.permission.RECORD_AUDIO))
//                Log.d("EXP","WHY ITS NOT WORKING")
                // A surface container using the 'background' color from the theme
                // MainScreen()
                window.statusBarColor= MaterialTheme.colors.primary.copy(alpha = 0.5f).toArgb()
                val navController = rememberNavController()
                RootNavigationGraph(navController = navController, mng, contentresolver) {
                    val ff = Intent(this@MainActivity, SecondActivity::class.java)
                    startActivity(ff)
                }
            }
        }


    }
}

//fun tryThis(value:Context){
//    val ff = Intent(this@MainActivity, SecondActivity::class.java)
//    startActivity(ff)
//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BengaliAppV2Theme {
    }
}