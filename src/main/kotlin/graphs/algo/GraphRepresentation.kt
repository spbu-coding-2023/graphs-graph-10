package graphs.algo

import graphs.primitives.Graph

/*
 * This method was originally written by Danil Usoltsev [https://github.com/Sibiri4ok]
 */
fun <V, E> toAdjacencyList(graph: Graph<V, E>):
        MutableMap< V, MutableSet<Pair<V, E?>> > {

    val d = mutableMapOf< V, MutableSet<Pair<V, E?>> >()
    graph.edges.forEach {
        val firstEdge = it.vertices.first.element
        val secondEdge = it.vertices.second.element
        if (!d.containsKey(firstEdge))
            d[firstEdge] = mutableSetOf(
                Pair(secondEdge, it.weight)
            )
        else
            d[firstEdge]?.add(
                Pair(secondEdge, it.weight)
            )

        if (!d.containsKey(secondEdge))
            d[secondEdge] = mutableSetOf(
                Pair(firstEdge, it.weight)
            )
        else
            d[secondEdge]?.add(
                Pair(firstEdge, it.weight)
            )
    }
    return d
}
