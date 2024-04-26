package viewmodel.graph

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class CircularPlacementStrategy : RepresentationStrategy {
    override fun <V> place(width: Double, height: Double, vertices: Collection<VertexViewModel<V>>) {
        if (vertices.isEmpty()) {
            println("CircularPlacementStrategy.place: there is nothing to place 👐🏻")
            return
        }

        val center = Pair(width / 2, height / 2)
        val angle = 2 * Math.PI / vertices.size

        val w = width - 50
        val h = height - 50

        val first = vertices.first()
        var point = Pair(center.first, center.second - min(w, h) / 2 + 50)
        first.x = point.first.dp
        first.y = point.second.dp
        first.color = Color.Gray

        vertices
            .drop(1)
            .onEach {
                point = point.rotate(center, angle)
                it.x = point.first.dp
                it.y = point.second.dp
            }
    }

    override fun <V> onResize(width: Double, height: Double, vertices: Collection<VertexViewModel<V>>) {
        TODO("Not yet implemented")
    }

    private fun Pair<Double, Double>.rotate(pivot: Pair<Double, Double>, angle: Double): Pair<Double, Double> {
        val sin = sin(angle)
        val cos = cos(angle)

        val diff = first - pivot.first to second - pivot.second
        val rotated = Pair(
            diff.first * cos - diff.second * sin,
            diff.first * sin + diff.second * cos,
        )
        return rotated.first + pivot.first to rotated.second + pivot.second
    }
}