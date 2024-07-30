package graphs.primitives

interface Edge {
    var element: Long
    var weight: Long?
    val vertices: Pair<Vertex, Vertex>
}
