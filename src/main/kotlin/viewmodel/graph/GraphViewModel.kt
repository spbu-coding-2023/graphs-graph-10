package viewmodel.graph

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import graphs.primitives.Graph

class GraphViewModel(
    val graph: Graph
) {
    private val _vertices = graph.vertices.associateWith { v ->
        VertexViewModel(0.dp, 0.dp, Color.Gray, v)
    }
    private val _edges = graph.edges.associateWith { e ->
        val fst = _vertices[e.vertices.first]
            ?: throw IllegalStateException("VertexView for ${e.vertices.first} not found")
        val snd = _vertices[e.vertices.second]
            ?: throw IllegalStateException("VertexView for ${e.vertices.second} not found")
        EdgeViewModel(fst, snd, e)
    }

    val vertices: Collection<VertexViewModel>
        get() = _vertices.values

    val edges: Collection<EdgeViewModel>
        get() = _edges.values

    val pickedVertices = mutableSetOf<Long>()
}
