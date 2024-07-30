package graphs.primitives

interface Graph {
    val vertices: Collection<Vertex>
    val edges: Collection<Edge>

    fun addVertex(v: Long, data: String? = ""): Vertex
    fun addEdge(u: Long, v: Long, e: Long, weight: Long? = null)
}
