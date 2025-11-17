package tw.edu.pu.csim.tcyang.race

import kotlin.random.Random

data class Horse(
    val n: Int, // 馬匹索引 (0, 1, 2)
    var HorseX: Int = 0,
    var HorseY: Int = 100 + 320 * n, // 確保 Y 軸間距
    var HorseNo: Int = 0 // 圖片編號 (0-3)
) {
    fun Run(randomMove: Int): Horse {
        val newHorseNo = (HorseNo + 1) % 4
        val newHorseX = HorseX + randomMove
        // 返回一個新的 Horse 物件，供 ViewModel 更新狀態
        return this.copy(HorseX = newHorseX, HorseNo = newHorseNo)
    }
}