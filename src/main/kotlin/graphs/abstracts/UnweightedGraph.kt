package graphs.abstracts

import graphs.primitives.Graph

abstract class UnweightedGraph : Graph {
    private val _vertices = hashMapOf<Long, Vertex>()
    private val _edges = hashMapOf<Long, Edge>()

    override val vertices: Collection<Vertex>
        get() = _vertices.values

    override val edges: Collection<Edge>
        get() = _edges.values

    override fun addVertex(v: Long): Vertex = _vertices.getOrPut(v) { Vertex(v) }

    override fun addEdge(u: Long, v: Long, e: Long, weight: Long?) {
        val first = addVertex(u)
        val second = addVertex(v)
        _edges.getOrPut(e) { Edge(e, first, second) }
    }
}
