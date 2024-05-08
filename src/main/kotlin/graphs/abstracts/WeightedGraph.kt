package graphs.abstracts

import graphs.primitives.Graph

abstract class WeightedGraph<V, E> : Graph<V, E> {
    private val _vertices = hashMapOf<V, Vertex<V>>()
    private val _edges = hashMapOf<E, Edge<E, V>>()

    override val edges: Collection<Edge<E, V>>
        get() = _edges.values


    override val vertices: Collection<Vertex<V>>
        get() = _vertices.values

    private val _weights = hashMapOf<E, Int?>()

    override fun addVertex(v: V): Vertex<V> = _vertices.getOrPut(v) { graphs.abstracts.Vertex(v) }

    override fun addEdge(u: V, v: V, e: E, weight: Int?) {

        val first = addVertex(u)
        val second = addVertex(v)
        _edges.getOrPut(e) { Edge(e, first, second) }
        //I set default value of weight to 1 if weight was  not declared, both edge and its weight are accessible by the same key
        if (weight == null) {
            _weights.getOrPut(e) { 1 }

        } else {
            _weights.getOrPut(e) { weight }
        }
    }

    abstract fun MinimalPathDeikstra(start: V, target: V): WeightedGraph<V, E>
    abstract fun MinimalPathFloidBellman(start: V, target: V): WeightedGraph<V, E>
}