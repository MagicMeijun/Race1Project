package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    var circleX = mutableFloatStateOf(100f)
    var circleY = mutableFloatStateOf(500f)

    var screenWidthPx = 1080f
    var screenHeightPx = 1920f

    var score = mutableIntStateOf(0)

    // 設定螢幕大小
    fun SetGameSize(width: Float, height: Float) {
        screenWidthPx = width
        screenHeightPx = height
    }

    // 移動紅球
    fun MoveCircle(dx: Float, dy: Float) {
        circleX.value += dx
        circleY.value += dy

        if (circleX.value < 100f) circleX.value = 100f

        // 如果碰到右邊邊界，分數 +1，紅球回到左邊
        if (circleX.value + 100f >= screenWidthPx) {
            score.value += 1
            circleX.value = 100f
        }
    }

    // 開始自動水平移動遊戲
    fun StartGame() {
        viewModelScope.launch {
            while (true) {
                circleX.value += 5f
                if (circleX.value + 100f >= screenWidthPx) {
                    score.value += 1
                    circleX.value = 100f
                }
                delay(16L) // 60fps
            }
        }
    }

    fun ResetGame() {
        score.value = 0
        circleX.value = 100f
        circleY.value = 500f
    }
}
