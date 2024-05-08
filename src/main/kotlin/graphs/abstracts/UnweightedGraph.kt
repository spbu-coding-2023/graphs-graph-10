package graphs.abstracts

import graphs.primitives.Graph

abstract class UnweightedGraph<V, E> : Graph<V, E> {
    private val _vertices = hashMapOf<V, Vertex<V>>()
    private val _edges = hashMapOf<E, Edge<E, V>>()

    override val vertices: Collection<Vertex<V>>
        get() = _vertices.values

    override val edges: Collection<Edge<E, V>>
        get() = _edges.values

    override fun addVertex(v: V): Vertex<V> = _vertices.getOrPut(v) { Vertex(v) }

    override fun addEdge(u: V, v: V, e: E, weight: Int?) {
        val first = addVertex(u)
        val second = addVertex(v)
        _edges.getOrPut(e) { Edge(e, first, second) }
    }
}