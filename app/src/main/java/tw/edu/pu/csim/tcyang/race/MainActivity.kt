package tw.edu.pu.csim.tcyang.race

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tw.edu.pu.csim.tcyang.race.ui.theme.RaceTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //強迫橫式螢幕
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        // 隱藏狀態列：獲取 WindowInsetsController，再隱藏statusBars
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

        setContent {
            RaceTheme {
                GameScreen(message = "text")
                }
            }
        }
    }