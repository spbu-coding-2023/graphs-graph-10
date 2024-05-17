package graphs.algo

import graphs.primitives.Graph
import graphs.types.DirectedGraph
import graphs.types.WeightedDirectedGraph

/*
 * This method was originally written by Danil Usoltsev [https://github.com/Sibiri4ok]
 */
fun <V, E> toAdjacencyList(graph: Graph<V, E>):
        MutableMap< V, MutableSet<Pair<V, Long?>> > {

    val d = mutableMapOf< V, MutableSet<Pair<V, Long?>> >()
    graph.vertices.forEach {
        d[it.element] = mutableSetOf()
    }
    if (graph is DirectedGraph || graph is WeightedDirectedGraph) {
        graph.edges.forEach {
            val firstEdge = it.vertices.first.element
            val secondEdge = it.vertices.second.element
            d[firstEdge]?.add(
                Pair(secondEdge, it.weight)
            )
        }
    } else {
        graph.edges.forEach {
            val firstEdge = it.vertices.first.element
            val secondEdge = it.vertices.second.element
            d[firstEdge]?.add(
                Pair(secondEdge, it.weight)
            )
            d[secondEdge] = mutableSetOf(
                Pair(firstEdge, it.weight)
            )
        }
    }
    return d
}
