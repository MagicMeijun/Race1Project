package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun GameScreen(gameViewModel: GameViewModel) { // 移除 message 參數，改用 ViewModel 內的 gameMessage

    // 1. 取得螢幕尺寸並通知 ViewModel
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current.density
    val screenWidthPx = (configuration.screenWidthDp * density)
    val screenHeightPx = (configuration.screenHeightDp * density)

    LaunchedEffect(screenWidthPx, screenHeightPx) {
        gameViewModel.SetGameSize(screenWidthPx, screenHeightPx)
    }

    // 2. 載入圖片資源 (假設 R.drawable 存在)
    val imageBitmaps = listOf(
        ImageBitmap.imageResource(R.drawable.horse0),
        ImageBitmap.imageResource(R.drawable.horse1),
        ImageBitmap.imageResource(R.drawable.horse2),
        ImageBitmap.imageResource(R.drawable.horse3)
    )

    // 3. 判斷按鈕文字和行為
    val isGameFinished = gameViewModel.winnerHorse != 0
    val buttonText = if (isGameFinished) "再玩一局" else "遊戲開始"
    val onButtonClick = {
        if (isGameFinished) {
            gameViewModel.ResetGame()
        } else {
            gameViewModel.StartGame()
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume() // 告訴系統已經處理了這個事件
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            // 繪製終點線
            val finishLineX = gameViewModel.finishLineX
            drawLine(
                color = Color.Green,
                start = Offset(finishLineX, 0f),
                end = Offset(finishLineX, size.height),
                strokeWidth = 15f
            )

            // 繪製圓形
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )

            // 繪製馬匹
            gameViewModel.horses.forEach { horse ->
                drawImage(
                    image = imageBitmaps[horse.HorseNo],
                    dstOffset = IntOffset(horse.HorseX, horse.HorseY),
                    dstSize = IntSize(300, 300)
                )
            }
        }

        Text(
            text = "資管二B 李維駿",
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        // 顯示遊戲訊息 (包括獲勝結果)
        Text(
            text = gameViewModel.gameMessage,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 80.dp, start = 16.dp)
        )

        // 遊戲控制按鈕
        Button(
            onClick = onButtonClick,
            // 遊戲進行中或獲勝後才允許點擊（進行中時禁用，獲勝後啟用重設）
            enabled = !gameViewModel.gameRunning || isGameFinished,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        ){
            Text(buttonText)
        }
    }
}