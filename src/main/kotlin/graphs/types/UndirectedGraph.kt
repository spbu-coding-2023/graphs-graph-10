package graphs.types


import graphs.primitives.Graph
import graphs.abstracts.Edge
import graphs.abstracts.Vertex

internal class UnweightedUndirectedGraph<V, E> : Graph<V, E> {
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

    override fun findcycle(v: V): Graph<V, E> {
        TODO("Not yet implemented")
    }

}