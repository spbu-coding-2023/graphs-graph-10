package viewmodel.graph

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import graphs.primitives.Graph

class GraphViewModel<V, E>(
    val graph: Graph<V, E>
) {
    private val _vertices = graph.vertices.associateWith { v ->
        VertexViewModel(0.dp, 0.dp, Color.Gray, v)
    }
     val _edges = graph.edges.associateWith { e ->
        val fst = _vertices[e.vertices.first]
            ?: throw IllegalStateException("VertexView for ${e.vertices.first} not found")
        val snd = _vertices[e.vertices.second]
            ?: throw IllegalStateException("VertexView for ${e.vertices.second} not found")
        EdgeViewModel(fst, snd, e)
    }

    val vertices: Collection<VertexViewModel<V>>
        get() = _vertices.values

    val edges: Collection<EdgeViewModel<E, V>>
        get() = _edges.values

    val pickedVertices = mutableSetOf<V>()
}
