package graphs.algo

import graphs.primitives.Edge
import graphs.primitives.Graph
import graphs.primitives.Vertex

import kotlin.math.abs

fun leaderRank(graph: Graph, d: Double, epsilon: Double): List<Pair<Vertex, Double>> {
    val adjacencyList = toAdjacencyList(graph)
    val n = graph.vertices.size
    val s = graph.addVertex(-1L)
    graph.vertices.forEach { vertex ->
        if (vertex.element != s.element) {
            graph.addEdge(s.element, vertex.element, -1L, 1L)
            graph.addEdge(vertex.element, s.element, -1L, 1L)
        }
    }

    var rate = mutableMapOf<Vertex, Double>()
    graph.vertices.forEach { vertex -> rate[vertex] = 1.0 / (n + 1) }

    val deg = mutableMapOf<Long, Int>()
    adjacencyList.forEach { (vertex, neighbors) ->
        deg[vertex] = neighbors.size
    }

    var diff = Double.MAX_VALUE
    while (diff >= epsilon) {
        val rateNew = mutableMapOf<Vertex, Double>()
        graph.vertices.forEach { v ->
            var sum = 0.0
            adjacencyList.forEach { (u, neighbors) ->
                if (neighbors.any { it.first == v.element }) {
                    val weight = neighbors.find { it.first == v.element }?.second?.toDouble() ?: 0.0
                    sum += (d * weight / (deg[u]?.toDouble() ?: 1.0)) * (rate[graph.vertices.find { it.element == u } ?: s]
                        ?: 0.0)
                }
            }
            rateNew[v] = (1 - d) / (n + 1) + sum
        }

        diff = 0.0
        graph.vertices.forEach { v ->
            diff += abs(rateNew[v]!! - rate[v]!!)
        }

        rate = rateNew
    }
    remove(graph, s)
    rate.remove(s)

    val sortedRatings = rate.toList().sortedByDescending { it.second }
    return sortedRatings
}

fun remove(graph: Graph, vertex: Vertex) {
    val edgesToRemove = mutableListOf<Edge>()
    graph.edges.forEach { edge ->
        if (edge.vertices.first == vertex || edge.vertices.second == vertex) {
            edgesToRemove.add(edge)
        }
    }
    edgesToRemove.forEach { edge ->
        (graph.edges as MutableCollection<Edge>).remove(edge)
    }
    (graph.vertices as MutableCollection<Vertex>).remove(vertex)
}
