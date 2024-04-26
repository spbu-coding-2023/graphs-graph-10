package viewmodel.graph

import model.graph.Edge

class EdgeViewModel<E, V>(
    val u: VertexViewModel<V>,
    val v: VertexViewModel<V>,
    private val e: Edge<E, V>,
)
