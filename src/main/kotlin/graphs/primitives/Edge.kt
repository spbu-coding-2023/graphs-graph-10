package graphs.primitives

interface Edge<E, V> {
    var element: E
    var weight: E?
    val vertices: Pair<Vertex<V>, Vertex<V>>
}
