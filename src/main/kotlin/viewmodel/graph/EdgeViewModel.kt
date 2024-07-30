package viewmodel.graph

import androidx.compose.ui.graphics.Color
import graphs.primitives.Edge

class EdgeViewModel(
    val u: VertexViewModel,
    val v: VertexViewModel,
    val e: Edge,
    var color: Color = Color.Black,
    var width: Float = 1f
)
