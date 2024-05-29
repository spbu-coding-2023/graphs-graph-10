package viewmodel

import androidx.compose.animation.core.*
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import layouts.YifanHuPlacementStrategy
import graphs.primitives.Graph
import viewmodel.graph.GraphViewModel

@Composable
fun animateDpOffsetAsState(
    targetValue: DpOffset,
    animationSpec: AnimationSpec<DpOffset>,
    label: String = "DpOffsetAnimation",
    finishedListener: ((DpOffset) -> Unit)? = null
): State<DpOffset> {
    return animateValueAsState(
        targetValue,
        DpOffset.VectorConverter,
        animationSpec,
        label = label,
        finishedListener = finishedListener
    )
}

class MainScreenViewModel(var graph: Graph) {
    private val representationStrategy = YifanHuPlacementStrategy()
    var graphViewModel = GraphViewModel(graph)
    var scale = mutableStateOf(1f)
    var offset = mutableStateOf(DpOffset.Zero)
//    var offset = animateDpOffsetAsState(DpOffset.Zero, tween(200, 0, LinearOutSlowInEasing))
    var displayWeight = mutableStateOf(false)
    var runLayout = false


    fun runLayoutAlgorithm(cords: Pair<Int, Int>) {
        scale.value = representationStrategy.place(cords.first.toDouble(), cords.second.toDouble(), graphViewModel)
            .toFloat()
    }

    fun updateOnResize(old: Pair<Int, Int>, new: Pair<Int, Int>) {
        representationStrategy.move(old, new, graphViewModel.vertices)
    }

    fun restoreGraphState() {
        graphViewModel.edges.forEach { e ->
            e.color = Color.Black
            e.width = 1f
        }
        graphViewModel.vertices.forEach { v ->
            v.color = Color.Gray
        }
        graphViewModel.pickedVertices.clear()
    }
}
