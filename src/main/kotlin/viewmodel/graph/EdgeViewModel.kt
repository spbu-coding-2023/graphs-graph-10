package viewmodel.graph

import androidx.compose.ui.graphics.Color
import graphs.primitives.Edge

class EdgeViewModel<E, V>(
    val u: VertexViewModel<V>,
    val v: VertexViewModel<V>,
    val e: Edge<E, V>,
    var color: Color = Color.Black,
    var width: Float = 1f
)
