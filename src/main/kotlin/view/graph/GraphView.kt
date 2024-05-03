package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import viewmodel.graph.GraphViewModel

@Composable
fun <V, E> GraphView(
    viewModel: GraphViewModel<V, E>,
    displayGraph: MutableState<Boolean>
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
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