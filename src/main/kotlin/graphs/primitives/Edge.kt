package model.graph

interface Edge<E, V> {
    var element: E
    val vertices: Pair<Vertex<V>, Vertex<V>>
}
