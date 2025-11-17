package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)
    var winnerHorse by mutableStateOf(0) // 1, 2, 3 表示獲勝馬匹，0 表示無

    // 遊戲訊息：用於顯示獲勝結果
    var gameMessage by mutableStateOf("請點擊『遊戲開始』")

    // 終點線位置 (距離右邊 350f)
    var finishLineX by mutableStateOf(0f)

    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf(0f)

    // 使用 mutableStateOf 確保 Compose 能響應列表變化
    var horses by mutableStateOf(mutableListOf<Horse>())

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        if (screenWidthPx != w || screenHeightPx != h) {
            screenWidthPx = w
            screenHeightPx = h

            // 初始化馬匹列表
            if (horses.isEmpty()) {
                val newHorses = mutableListOf<Horse>()
                for (i in 0..2){
                    newHorses.add(Horse(i))
                }
                horses = newHorses
            }
            // 計算終點線位置 (馬匹寬度 300f + 邊距 50f)
            finishLineX = screenWidthPx - 350f
        }
    }

    fun StartGame() {
        if (gameRunning) return // 避免重複啟動
        ResetGame() // 確保在開始前重設狀態
        gameRunning = true
        gameMessage = "比賽進行中..."


        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)

                // 圓形移動 (用戶控制的圓形繼續動畫，與賽馬獨立)
                circleX += 10f
                if (circleX >= screenWidthPx - 100){
                    circleX = 100f
                }

                // 賽馬移動邏輯
                MoveHorses()

                if (winnerHorse != 0) {
                    gameRunning = false // 停止遊戲
                    gameMessage = "第${winnerHorse}馬獲勝"
                }
            }
        }
    }

    private fun MoveHorses() {
        var currentWinner = 0

        val updatedHorses = horses.mapIndexed { index, horse ->
            val randomMove = Random.nextInt(10, 31)
            val newHorse = horse.Run(randomMove)

            // 判斷是否抵達終點
            if (currentWinner == 0 && newHorse.HorseX.toFloat() >= finishLineX) {
                currentWinner = index + 1 // 1-based index
            }
            return@mapIndexed newHorse
        }.toMutableList()

        horses = updatedHorses // 替換列表觸發 UI 更新

        if (currentWinner != 0) {
            winnerHorse = currentWinner
        }
    }

    fun ResetGame() {
        // 將馬匹 X 軸全部設為 0
        horses = horses.map { horse ->
            horse.copy(HorseX = 0, HorseNo = 0)
        }.toMutableList()

        winnerHorse = 0
        gameRunning = false
        gameMessage = "請點擊『遊戲開始』進行下一輪賽馬"

        // 重設圓形位置
        circleX = 100f
        circleY = screenHeightPx - 100f
    }

    fun MoveCircle(x: Float, y: Float) {
        // 限制圓形移動範圍 (可選，但建議)
        circleX = (circleX + x).coerceIn(100f, screenWidthPx - 100f)
        circleY = (circleY + y).coerceIn(100f, screenHeightPx - 100f)
    }
}