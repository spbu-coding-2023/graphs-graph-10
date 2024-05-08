package graphs.abstracts

import graphs.primitives.Edge
import graphs.primitives.Vertex

class Edge<E, V>(
    override var element: E,
    var first: Vertex<V>,
    var second: Vertex<V>,
) : Edge<E, V> {
    override val vertices
        get() = first to second
}