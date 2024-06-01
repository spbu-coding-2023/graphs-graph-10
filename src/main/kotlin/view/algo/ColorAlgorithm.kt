package view.algo

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

fun generateRandomColors(numColors: Int): List<Color> {
    val random = Random(System.currentTimeMillis())
    val colorList = mutableListOf<Color>()
    var count = 0

    while (count < numColors) {
        val red = random.nextInt(256)
        val green = random.nextInt(256)
        val blue = random.nextInt(256)
        val color = Color(red, green, blue)
        if (color !in colorList) {
            colorList.add(color)
            count++
        }
    }
    return colorList
}
