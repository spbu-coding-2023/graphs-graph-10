package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.IntOffset
import viewmodel.graph.GraphViewModel
import kotlin.math.roundToInt

@Composable
fun <V, E> GraphView(
    viewModel: GraphViewModel<V, E>,
    displayGraph: MutableState<Boolean>,
    state: TransformableState,
    scale: Float,
    offsetX: Float,
    offsetY: Float
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale
        )
        .transformable(state = state)
        .offset {
            IntOffset(
                offsetX.roundToInt(),
                offsetY.roundToInt()
            )
        }
    ) {
        if (displayGraph.value) {
            viewModel.edges.forEach { e ->
                EdgeView(e, Modifier)
            }
            viewModel.vertices.forEach { v ->
                VertexView(v, Modifier)
            }
        }
    }
}