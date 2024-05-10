package graphs.algo

import graphs.primitives.Graph

/*
 * This method was originally written by Danil Usoltsev [https://github.com/Sibiri4ok]
 */
fun <V> toAdjacencyList(graph: Graph<V, *>): MutableMap<V, MutableSet<V>> {
    val d = mutableMapOf<V, MutableSet<V>>()
    graph.edges.forEach {
        val firstEdge = it.vertices.first.element
        val secondEdge = it.vertices.second.element
        if (!d.containsKey(firstEdge))
            d[firstEdge] = mutableSetOf(secondEdge)
        else
            d[firstEdge]?.add(secondEdge)

        if (!d.containsKey(secondEdge))
            d[secondEdge] = mutableSetOf(firstEdge)
        else
            d[secondEdge]?.add(firstEdge)
    }
    return d
}
