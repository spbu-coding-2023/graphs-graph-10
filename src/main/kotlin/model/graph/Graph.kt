package model.graph

interface Graph<V, E> {
    val vertices: Collection<Vertex<V>>
    val edges: Collection<Edge<E, V>>

    fun addVertex(v: V): Vertex<V>
    fun addEdge(u: V, v: V, e: E): Edge<E, V>
}
