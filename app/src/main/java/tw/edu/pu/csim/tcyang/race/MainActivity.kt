package tw.edu.pu.csim.tcyang.race

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import tw.edu.pu.csim.tcyang.race.ui.theme.RaceTheme

class MainActivity : ComponentActivity() {

    // 初始化 ViewModel
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 強迫橫式螢幕 (已保留)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        // 隱藏狀態列和導航列 (已保留)
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // *** 移除在 Activity 中手動計算和設定尺寸的程式碼 ***
        // *** 讓 GameScreen 在 Compose 中處理尺寸設定 ***
        /*
        val windowMetricsCalculator = WindowMetricsCalculator.getOrCreate()
        val currentWindowMetrics= windowMetricsCalculator.computeCurrentWindowMetrics(this)
        val bounds = currentWindowMetrics.bounds
        val screenWidthPx = bounds.width().toFloat()
        val screenHeightPx = bounds.height().toFloat()
        gameViewModel.SetGameSize(screenWidthPx , screenHeightPx) // 移除此行
        */

        setContent {
            RaceTheme {
                // *** 修正: 移除不再需要的 'message' 參數 ***
                GameScreen(gameViewModel = gameViewModel)
            }
        }
    }
}