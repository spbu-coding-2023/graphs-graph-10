package view.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.DpOffset
import graphs.types.DirectedGraph
import graphs.types.WeightedDirectedGraph
import view.graph.edge.DirectedEdgeView
import view.graph.edge.EdgeView
import viewmodel.graph.GraphViewModel

@Composable
fun GraphView(
    graphViewModel: GraphViewModel,
    displayGraph: MutableState<Boolean>,
    displayWeight: MutableState<Boolean>,
    scale: Float,
    offset: DpOffset
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.LightGray)
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale
        )
        .offset(offset.x, offset.y)
    ) {
        if (displayGraph.value) {
            if (graphViewModel.graph is DirectedGraph
                || graphViewModel.graph is WeightedDirectedGraph) {
                graphViewModel.edges.forEach { e ->
                    DirectedEdgeView(e, displayWeight, Modifier)
                }
            } else {
                graphViewModel.edges.forEach { e ->
                    EdgeView(e, displayWeight, Modifier)
                }
            }
            graphViewModel.vertices.forEach { v ->
                VertexView(v, Modifier, graphViewModel)
            }
        }
    }
}
