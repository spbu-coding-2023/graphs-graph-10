package graphs.abstracts

import graphs.primitives.Edge
import graphs.primitives.Vertex

class Edge(
    override var element: Long,
    var first: Vertex,
    var second: Vertex,
    override var weight: Long? = null
) : Edge {
    override val vertices
        get() = first to second
}
