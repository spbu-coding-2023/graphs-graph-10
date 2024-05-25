package graphs.abstracts

import graphs.primitives.Graph

abstract class WeightedGraph : Graph {
    private val _vertices = hashMapOf<Long, Vertex>()
    private val _edges = hashMapOf<Long, Edge>()

    override val edges: Collection<Edge>
        get() = _edges.values


    override val vertices: Collection<Vertex>
        get() = _vertices.values

    override fun addVertex(v: Long): Vertex = _vertices.getOrPut(v) { graphs.abstracts.Vertex(v) }

    override fun addEdge(u: Long, v: Long, e: Long, weight: Long?) {

        val first = addVertex(u)
        val second = addVertex(v)
        //I set default value of weight to 1 if weight was  not declared, both edge and its weight are accessible by the same key
        if (weight == null) {
            _edges.getOrPut(e) { Edge(e, first, second, 1) }
        } else {
            _edges.getOrPut(e) { Edge(e, first, second, weight) }
        }
    }

    abstract fun MinimalPathFloidBellman(start: Long, target: Long): WeightedGraph
}
