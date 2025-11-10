package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {
        // 🔴 紅球自動水平移動
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX.value, gameViewModel.circleY.value)
            )
        }

        // 📝 中間上方文字
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(
                text = "作者：李維駿 411300467",
                fontSize = 24.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        // 💯 右上角分數
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, end = 20.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "分數：${gameViewModel.score.value}",
                fontSize = 24.sp,
                color = Color.Black
            )
        }

        // 📱 左上角螢幕大小
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = "螢幕：${gameViewModel.screenWidthPx.toInt()} x ${gameViewModel.screenHeightPx.toInt()}",
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        // ▶️ 遊戲開始按鈕
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
        ) {
            Button(onClick = { gameViewModel.StartGame() }) {
                Text("遊戲開始")
            }
        }
    }
}